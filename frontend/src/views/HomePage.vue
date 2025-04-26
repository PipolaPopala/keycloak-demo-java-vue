<script setup>
import { useRouter } from 'vue-router';
import { useAuthStore } from '../stores/auth';
import { computed } from 'vue';

const router = useRouter();
const authStore = useAuthStore();

// Получаем состояние аутентификации из хранилища
const isAuthenticated = computed(() => authStore.isAuthenticated);

const goToProtectedArea = async () => {
  // Проверяем статус аутентификации
  const isAuthenticated = await authStore.checkAuth();

  if (isAuthenticated) {
    router.push('/private');
  } else {
    // Перенаправляем на страницу входа с указанием целевого маршрута
    router.push({ name: 'Login', query: { redirect: '/private' } });
  }
};
</script>

<template>
  <div class="home-container">
    <h2>Главная страница</h2>
    <div class="content">
      <p>Добро пожаловать в демонстрационное приложение Keycloak + Spring Boot + Vue.js!</p>
      <p>Эта страница доступна всем пользователям без авторизации.</p>

      <div class="actions">
        <h3>Действия</h3>
        <button @click="goToProtectedArea" class="action-button">Перейти в защищенную область</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.home-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

h2 {
  color: #333;
  text-align: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #ddd;
}

.content {
  padding: 15px 0;
}

ul {
  list-style-type: none;
  padding-left: 0;
}

li {
  margin-bottom: 10px;
}

a {
  color: #4a90e2;
  text-decoration: none;
  font-weight: 500;
}

a:hover {
  text-decoration: underline;
}

.action-button {
  background-color: #4a90e2;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.action-button:hover {
  background-color: #3a7bc8;
}
</style>