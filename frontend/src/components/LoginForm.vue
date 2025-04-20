<template>
  <div class="login-form">
    <h2>Вход</h2>
    <form @submit.prevent="handleLogin">
      <input v-model="form.username" type="text" placeholder="Логин" required />
      <input
        v-model="form.password"
        type="password"
        placeholder="Пароль"
        required
      />
      <button type="submit" :disabled="loading">
        {{ loading ? "Вход..." : "Войти" }}
      </button>
    </form>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import keycloak from "../keycloak";

const emit = defineEmits(["login-success"]);
const router = useRouter();
const form = ref({
  username: "",
  password: "",
});
const error = ref("");
const loading = ref(false);

const handleLogin = async () => {
  error.value = "";
  loading.value = true;

  try {
    const response = await axios.post(
      `${import.meta.env.VITE_KEYCLOAK_URL}/realms/${
        import.meta.env.VITE_KEYCLOAK_REALM
      }/protocol/openid-connect/token`,
      new URLSearchParams({
        client_id: import.meta.env.VITE_KEYCLOAK_CLIENT,
        client_secret: import.meta.env.VITE_CLIENT_SECRET,
        username: form.value.username,
        password: form.value.password,
        grant_type: "password",
      }),
      {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        withCredentials: true,
      }
    );

    console.log("Login response:", response.data);
    // Добавьте проверку ответа
    if (!response.data.access_token) {
      throw new Error("No token received");
    }

    // Обязательно обновите все поля
    keycloak.token = response.data.access_token;
    keycloak.refreshToken = response.data.refresh_token;
    keycloak.idToken = response.data.id_token;
    keycloak.authenticated = true;

    // Принудительно обновим состояние
    window.dispatchEvent(new Event("keycloakUpdate"));

    await router.push("/private");
  } catch (err) {
    error.value = "Неверный логин или пароль";
    console.error("Login error:", err);
  } finally {
    loading.value = false;
  }
};
</script>

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
