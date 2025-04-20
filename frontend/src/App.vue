<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import keycloak from "./keycloak";
import LoginForm from "./components/LoginForm.vue";

const router = useRouter();
const isAuthenticated = ref(false);

const initAuth = async () => {
  try {
    const authenticated = await keycloak.init();

    isAuthenticated.value = authenticated;
    if (authenticated) {
      router.push("/private");
    }
  } catch (error) {
    console.error("Authentication failed:", error);
  }
};

onMounted(() => {
  window.addEventListener("keycloakUpdate", () => {
    isAuthenticated.value = keycloak.authenticated;
  });
  initAuth();
});
</script>

<template>
  <div class="container">
    <h1>Keycloak + Vue 3 Demo</h1>

    <LoginForm
      v-if="!isAuthenticated"
      @login-success="isAuthenticated = true"
    />

    <router-view v-else />
  </div>
</template>
