import { createRouter, createWebHistory } from 'vue-router'
import PrivatePage from './views/PrivatePage.vue'
import HomePage from './views/HomePage.vue'
import Login from './components/Login.vue'
import NotFound from './views/NotFound.vue'
import MainLayout from './layouts/MainLayout.vue'
import EmptyLayout from './layouts/EmptyLayout.vue'
import { useAuthStore } from './stores/auth'

const routes = [
  {
    path: '/',
    component: MainLayout,
    children: [
      {
        path: '',
        name: 'Home',
        component: HomePage,
        meta: { requiresAuth: false }
      },
      {
        path: 'login',
        name: 'Login',
        component: Login,
        meta: { requiresAuth: false }
      },
      {
        path: 'private',
        name: 'Private',
        component: PrivatePage,
        meta: { requiresAuth: true }
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    component: EmptyLayout,
    children: [
      {
        path: '',
        name: 'NotFound',
        component: NotFound,
        meta: { requiresAuth: false }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(async (to, from, next) => {
  // Получаем хранилище аутентификации
  const authStore = useAuthStore()

  // Проверяем статус аутентификации через хранилище
  const isAuthenticated = await authStore.checkAuth()

  // Если пользователь пытается перейти на страницу логина, будучи авторизованным
  if (to.name === 'Login' && isAuthenticated) {
    // Перенаправляем на главную страницу
    next({ name: 'Home' })
    return
  }

  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!isAuthenticated) {
      // Сохраняем целевой маршрут для перенаправления после входа
      next({
        name: 'Login',
        query: { redirect: to.fullPath }
      })
      return
    }

    // Пользователь аутентифицирован, разрешаем переход
    next()
  } else {
    // Для публичных страниц просто продолжаем
    next()
  }
})


export default router