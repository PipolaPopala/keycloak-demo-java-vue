package org.keycloak.keycloak_spring_boot_starter.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Фильтр для проверки JWT токенов
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Получаем заголовок Authorization
        String authHeader = request.getHeader("Authorization");
        
        // Проверяем наличие токена Bearer
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                // Извлекаем токен
                String token = authHeader.substring(7);
                
                // Декодируем и проверяем токен
                var jwt = jwtDecoder.decode(token);
                
                // Создаем аутентификацию
                var authentication = new JwtAuthenticationToken(jwt);
                
                // Устанавливаем аутентификацию в контекст безопасности
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                logger.debug("JWT токен успешно обработан для пользователя: {}", jwt.getSubject());
            } catch (Exception e) {
                logger.error("Ошибка при обработке JWT токена: {}", e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }
        
        // Продолжаем цепочку фильтров
        filterChain.doFilter(request, response);
    }
}