<script setup>
// Пустой лейаут без шапки и навигации
</script>

<template>
  <div class="empty-layout">
    <router-view />
  </div>
</template>

<style scoped>
.empty-layout {
  width: 100%;
  min-height: 100%;
  display: flex;
  flex-direction: column;
}
</style>