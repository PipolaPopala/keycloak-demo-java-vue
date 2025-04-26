// src/services/apiService.js
import axios from 'axios';
import authService from './authService';

const API_URL = import.meta.env.VITE_API_URL;

// Создаем экземпляр axios с настройками
const apiClient = axios.create({
  baseURL: API_URL,
  withCredentials: true // Для отправки cookies
});

// Добавляем перехватчик для добавления токена к запросам
apiClient.interceptors.request.use(config => {
  const token = localStorage.getItem('access_token');
  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
});

// Добавляем перехватчик для обработки ошибок аутентификации
apiClient.interceptors.response.use(
  response => response,
  async error => {
    const originalRequest = error.config;
    
    // Если ошибка 401 (Unauthorized) и запрос еще не повторялся
    if (error.response && error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      
      try {
        // Пытаемся обновить токен
        const response = await authService.refreshToken();
        const newToken = response.data.accessToken;
        
        // Сохраняем новый токен
        localStorage.setItem('access_token', newToken);
        
        // Обновляем заголовок в текущем запросе
        originalRequest.headers['Authorization'] = `Bearer ${newToken}`;
        
        // Повторяем оригинальный запрос с новым токеном
        return apiClient(originalRequest);
      } catch (refreshError) {
        // Если не удалось обновить токен, перенаправляем на страницу входа
        localStorage.removeItem('access_token');
        window.location.href = '/';
        return Promise.reject(refreshError);
      }
    }
    
    return Promise.reject(error);
  }
);

export default {
  getProtectedResource() {
    return apiClient.get('/protected/resource');
  },
  validateToken() {
    return apiClient.get('/auth/validate');
  }
};