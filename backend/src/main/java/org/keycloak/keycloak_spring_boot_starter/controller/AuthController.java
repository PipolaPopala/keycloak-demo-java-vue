package org.keycloak.keycloak_spring_boot_starter.controller;

import org.keycloak.keycloak_spring_boot_starter.model.AuthRequest;
import org.keycloak.keycloak_spring_boot_starter.model.AuthResponse;
import org.keycloak.keycloak_spring_boot_starter.model.TokenResponse;
import org.keycloak.keycloak_spring_boot_starter.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Контроллер для обработки запросов аутентификации
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private KeycloakService keycloakService;
    
    /**
     * Эндпоинт для входа пользователя
     * @param authRequest запрос с учетными данными пользователя
     * @param response HTTP-ответ для установки cookie
     * @return информация о токене доступа
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        TokenResponse tokenResponse = keycloakService.getToken(authRequest.getUsername(), authRequest.getPassword());
        
        // Установка refresh token в httpOnly cookie
        Cookie refreshTokenCookie = new Cookie("refresh_token", tokenResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(86400); // 1 день
        response.addCookie(refreshTokenCookie);
        
        return ResponseEntity.ok(new AuthResponse(tokenResponse.getAccessToken(), tokenResponse.getTokenType()));
    }
    
    /**
     * Эндпоинт для выхода пользователя
     * @param refreshToken токен обновления из cookie
     * @param response HTTP-ответ для удаления cookie
     * @return статус операции
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "refresh_token", required = false) String refreshToken, 
                                   HttpServletResponse response) {
        // Вызов сервиса для выхода из Keycloak
        keycloakService.logout(refreshToken);
        
        // Удаление cookie refresh_token
        Cookie refreshTokenCookie = new Cookie("refresh_token", "");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0); // Удаление cookie
        response.addCookie(refreshTokenCookie);
        
        return ResponseEntity.ok().build();
    }
    
    /**
     * Эндпоинт для обновления токена доступа
     * @param refreshToken токен обновления из cookie
     * @param response HTTP-ответ для обновления cookie
     * @return новый токен доступа
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(value = "refresh_token", required = false) String refreshToken,
                                    HttpServletResponse response) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Refresh token is missing");
        }
        
        // Обновление токена через Keycloak
        TokenResponse tokenResponse = keycloakService.refreshToken(refreshToken);
        if (tokenResponse == null) {
            return ResponseEntity.badRequest().body("Failed to refresh token");
        }
        
        // Обновление cookie refresh_token
        Cookie refreshTokenCookie = new Cookie("refresh_token", tokenResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(86400); // 1 день
        response.addCookie(refreshTokenCookie);
        
        return ResponseEntity.ok(new AuthResponse(tokenResponse.getAccessToken(), tokenResponse.getTokenType()));
    }
    
    /**
     * Эндпоинт для валидации токена
     * @return статус валидности токена
     */
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken() {
        // Токен уже проверен фильтром, если мы здесь, значит токен валидный
        return ResponseEntity.ok().build();
    }
}