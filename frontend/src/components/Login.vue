<script setup>
import { ref, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useAuthStore } from '../stores/auth';

const username = ref('');
const password = ref('');
const router = useRouter();
const route = useRoute();
const authStore = useAuthStore();

// Получаем ошибку из хранилища
const error = computed(() => authStore.error);

const login = async () => {
  const success = await authStore.login(username.value, password.value);

  if (success) {
    // Проверяем, есть ли параметр redirect в URL
    const redirectPath = route.query.redirect || '/';
    router.push(redirectPath);
  }
};

</script>

<template>
  <div class="login-container">
    <form @submit.prevent="login" class="login-form">
      <h2>Вход в систему</h2>
      <div class="form-group">
        <label for="username">Имя пользователя</label>
        <input v-model="username" type="text" id="username" placeholder="Введите имя пользователя" required />
      </div>
      <div class="form-group">
        <label for="password">Пароль</label>
        <input v-model="password" type="password" id="password" placeholder="Введите пароль" required />
      </div>
      <button type="submit" class="login-button">Войти</button>
      <p v-if="error" class="error-message">{{ error }}</p>
      <div class="info-box">
        <p>Это демонстрационная страница входа. После авторизации вам станут доступны защищенные разделы.</p>
      </div>
    </form>
  </div>
</template>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

.login-form {
  width: 100%;
  max-width: 400px;
  padding: 25px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
  font-size: 24px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 6px;
  font-weight: 500;
  color: #333;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
  color: #333;
}

input:focus {
  outline: none;
  border-color: #4a90e2;
  box-shadow: 0 0 0 2px rgba(74, 144, 226, 0.2);
}

.login-button {
  width: 100%;
  padding: 10px;
  background-color: #4a90e2;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 10px;
  transition: background-color 0.3s;
}

.login-button:hover {
  background-color: #3a7bc8;
}

.error-message {
  color: #e53935;
  margin-top: 15px;
  text-align: center;
  font-weight: bold;
}

.info-box {
  margin-top: 20px;
  padding: 10px;
  background-color: #e9f2fd;
  border-radius: 4px;
  font-size: 14px;
  color: #333;
}
</style>