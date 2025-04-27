# Этап сборки
FROM gradle:8.7-jdk21 AS builder
WORKDIR /app

# Копируем файлы сборки
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Копируем исходный код
COPY src ./src

# Собираем приложение
RUN gradle build --no-daemon

# Этап запуска
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Копируем собранный JAR-файл из этапа сборки
COPY --from=builder /app/build/libs/*.jar app.jar

# Настройка переменных окружения для подключения к Keycloak и фронтенду
ENV SERVER_PORT=8888
ENV KEYCLOAK_AUTH_SERVER_URL=http://host.docker.internal:8080
ENV SPRING_WEB_CORS_ALLOWED_ORIGINS=http://host.docker.internal:8080,http://host.docker.internal:5173,http://localhost:5173

# Открываем порт
EXPOSE 8888

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]