<script setup>
import keycloak from "../keycloak";
import { useRouter } from "vue-router";

const router = useRouter();

const logout = () => {
  keycloak.logout();
  router.push("/");
};
</script>

<template>
  <div class="private-content">
    <h2>Приватные данные</h2>
    <p>Вы успешно авторизованы!</p>
    <button @click="logout">Выйти</button>
  </div>
</template>
