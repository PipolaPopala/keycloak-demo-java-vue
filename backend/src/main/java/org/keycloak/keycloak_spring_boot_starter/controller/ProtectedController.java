package org.keycloak.keycloak_spring_boot_starter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Контроллер для защищенных ресурсов
 */
@RestController
@RequestMapping("/api/protected")
public class ProtectedController {
    
    /**
     * Получение защищенного ресурса
     * @return защищенные данные
     */
    @GetMapping("/resource")
    public ResponseEntity<?> getProtectedResource(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Это защищенный ресурс");
        response.put("username", jwt.getClaimAsString("preferred_username"));
        response.put("roles", jwt.getClaimAsStringList("roles"));
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Получение информации о пользователе
     * @return данные пользователя из JWT токена
     */
    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("sub", jwt.getSubject());
        userInfo.put("username", jwt.getClaimAsString("preferred_username"));
        userInfo.put("email", jwt.getClaimAsString("email"));
        userInfo.put("name", jwt.getClaimAsString("name"));
        userInfo.put("roles", jwt.getClaimAsStringList("roles"));
        
        return ResponseEntity.ok(userInfo);
    }
}