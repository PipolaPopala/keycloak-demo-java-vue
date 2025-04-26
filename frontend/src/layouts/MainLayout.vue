<script setup>
import { computed } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "../stores/auth";

const router = useRouter();
const authStore = useAuthStore();

// Получаем состояние аутентификации из хранилища
const isAuthenticated = computed(() => authStore.isAuthenticated);

// Обработчик выхода из системы
const logout = async () => {
  await authStore.logout();
  router.push('/');
};
</script>

<template>
  <div class="app-container">
    <header class="app-header">
      <h1>Keycloak + Spring Boot + Vue 3 Demo</h1>
      <div class="auth-status">
        <span class="status-badge" v-if="isAuthenticated">Вы авторизованы</span>
        <span class="status-badge info" v-else>Вы не авторизованы</span>
      </div>
      <nav class="main-nav">
        <router-link to="/" class="nav-link">Главная</router-link>
        <template v-if="isAuthenticated">
          <router-link to="/private" class="nav-link">Приватная страница</router-link>
          <button @click="logout" class="nav-button">Выйти</button>
        </template>
        <template v-else>
          <router-link to="/login" class="nav-link">Вход</router-link>
        </template>
      </nav>
    </header>

    <main class="app-content">
      <router-view />
    </main>
  </div>
</template>

<style scoped>
.app-container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
}

.app-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #ddd;
}

.auth-status {
  margin: 10px 0;
}

.status-badge {
  background-color: #4caf50;
  color: white;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 14px;
}

.status-badge.info {
  background-color: #2196F3;
}

.main-nav {
  display: flex;
  gap: 15px;
  margin-top: 15px;
  flex-wrap: wrap;
  justify-content: center;
}

.nav-link {
  color: #4a90e2;
  text-decoration: none;
  font-weight: 500;
  padding: 8px 12px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.nav-link:hover {
  background-color: #e9f2fd;
}

.nav-button {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s;
}

.nav-button:hover {
  background-color: #d32f2f;
}

.app-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}
</style>