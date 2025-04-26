// src/services/authService.js
import axios from 'axios';

const API_URL = `${import.meta.env.VITE_API_URL}/auth`;

export default {
  login(username, password) {
    return axios.post(`${API_URL}/login`, { username, password });
  },
  logout() {
    return axios.post(`${API_URL}/logout`);
  },
  refreshToken() {
    return axios.post(`${API_URL}/refresh`);
  },
  validateToken() {
    return axios.get(`${API_URL}/validate`);
  }
};