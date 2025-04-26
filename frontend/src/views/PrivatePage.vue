<script setup>
import { useRouter } from "vue-router";
import { useAuthStore } from "../stores/auth";

const router = useRouter();
const authStore = useAuthStore();

const logout = async () => {
  try {
    await authStore.logout();
    router.push("/");
  } catch (error) {
    console.error('Ошибка при выходе:', error);
  }
};
</script>

<template>
  <div class="private-container">
    <h2>Приватная страница</h2>
    <div class="private-content">
      <div class="status-message">
        <p>Вы успешно авторизованы и получили доступ к приватной странице!</p>
        <p>Эта страница доступна только авторизованным пользователям.</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.private-container {
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

.private-content {
  margin-bottom: 20px;
}

.status-message {
  background-color: #e9f2fd;
  padding: 15px;
  border-radius: 4px;
  margin-bottom: 20px;
  text-align: center;
  color: #333;
}
</style>