import { createRouter, createWebHistory } from 'vue-router'
import PrivatePage from './views/PrivatePage.vue'
import keycloak from './keycloak'

const routes = [
  {
    path: '/private',
    component: PrivatePage,
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(async (to) => {
  if (to.meta.requiresAuth) {
    if (!keycloak.authenticated) {
      return '/'
    }
  }
})

export default router