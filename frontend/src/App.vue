<script setup>
import { onMounted } from "vue";
import { useAuthStore } from "./stores/auth";

const authStore = useAuthStore();

onMounted(() => {
  // Инициализируем состояние аутентификации при загрузке приложения
  authStore.initAuth();
});
</script>

<template>
  <router-view />
</template>
