// src/stores/auth.js
import { defineStore } from 'pinia';
import authService from '../services/authService';
import { ref } from 'vue';

export const useAuthStore = defineStore('auth', () => {
  const isAuthenticated = ref(false);
  const user = ref(null);
  const loading = ref(false);
  const error = ref('');

  // Инициализация состояния аутентификации
  const initAuth = async () => {
    loading.value = true;
    error.value = '';
    
    try {
      const token = localStorage.getItem('access_token');
      if (!token) {
        isAuthenticated.value = false;
        loading.value = false;
        return false;
      }
      
      // Проверяем валидность токена
      await authService.validateToken();
      isAuthenticated.value = true;
      loading.value = false;
      return true;
    } catch (err) {
      console.error('Ошибка при инициализации аутентификации:', err);
      localStorage.removeItem('access_token');
      isAuthenticated.value = false;
      error.value = 'Сессия истекла. Пожалуйста, войдите снова.';
      loading.value = false;
      return false;
    }
  };

  // Вход в систему
  const login = async (username, password) => {
    loading.value = true;
    error.value = '';
    
    try {
      const response = await authService.login(username, password);
      localStorage.setItem('access_token', response.data.accessToken);
      isAuthenticated.value = true;
      loading.value = false;
      return true;
    } catch (err) {
      console.error('Ошибка при входе:', err);
      error.value = 'Неверное имя пользователя или пароль';
      isAuthenticated.value = false;
      loading.value = false;
      return false;
    }
  };

  // Выход из системы
  const logout = async () => {
    loading.value = true;
    
    try {
      await authService.logout();
      localStorage.removeItem('access_token');
      isAuthenticated.value = false;
      user.value = null;
    } catch (err) {
      console.error('Ошибка при выходе:', err);
    } finally {
      loading.value = false;
    }
  };

  // Проверка аутентификации
  const checkAuth = async () => {
    if (isAuthenticated.value) return true;
    
    return await initAuth();
  };

  return {
    isAuthenticated,
    user,
    loading,
    error,
    login,
    logout,
    initAuth,
    checkAuth
  };
});