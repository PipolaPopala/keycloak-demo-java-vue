<script setup>
// Компонент для отображения страницы 404
</script>

<template>
  <div class="not-found-container">
    <div class="not-found-content">
      <h1>404</h1>
      <h2>Страница не найдена</h2>
      <p>Извините, запрашиваемая страница не существует или была перемещена.</p>
      <router-link to="/" class="home-button">Вернуться на главную</router-link>
    </div>
  </div>
</template>

<style scoped>
.not-found-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 70vh;
  text-align: center;
  padding: 20px;
}

.not-found-content {
  max-width: 600px;
  padding: 30px;
  border-radius: 8px;
  background-color: #f8f9fa;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 6rem;
  margin: 0;
  color: #dc3545;
}

h2 {
  font-size: 2rem;
  margin: 10px 0 20px;
  color: #343a40;
}

p {
  font-size: 1.1rem;
  margin-bottom: 30px;
  color: #6c757d;
}

.home-button {
  display: inline-block;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  border-radius: 4px;
  font-weight: 500;
  transition: background-color 0.3s;
}

.home-button:hover {
  background-color: #0056b3;
}
</style>