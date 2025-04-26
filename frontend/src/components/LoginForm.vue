<script setup>
import { ref, computed } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useAuthStore } from "../stores/auth";

const emit = defineEmits(["login-success"]);
const router = useRouter();
const route = useRoute();
const authStore = useAuthStore();

const form = ref({
  username: "",
  password: "",
});

// Получаем состояние загрузки и ошибки из хранилища
const loading = computed(() => authStore.loading);
const error = computed(() => authStore.error);

const handleLogin = async () => {
  try {
    const success = await authStore.login(form.value.username, form.value.password);

    if (success) {
      // Уведомляем родительский компонент об успешном входе
      emit("login-success");
      await router.push("/");
    }
  } catch (err) {
    console.error("Login error:", err);
    // Устанавливаем сообщение об ошибке для пользователя
    authStore.error = "Неверный логин или пароль";
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <div class="login-form">
    <h2>Вход</h2>
    <form @submit.prevent="handleLogin">
      <input v-model="form.username" type="text" placeholder="Логин" required />
      <input v-model="form.password" type="password" placeholder="Пароль" required />
      <button type="submit" :disabled="loading">
        {{ loading ? "Вход..." : "Войти" }}
      </button>
    </form>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<style scoped>
.login-form {
  max-width: 400px;
  margin: 2rem auto;
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 8px;
}

input {
  display: block;
  width: 100%;
  padding: 0.5rem;
  margin: 0.5rem 0;
  box-sizing: border-box;
}

button {
  width: 100%;
  padding: 0.75rem;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:disabled {
  background: #ccc;
}

.error {
  color: red;
  margin-top: 1rem;
}
</style>
