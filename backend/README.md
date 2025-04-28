# Бэкенд на Spring Boot с интеграцией Keycloak

Этот документ описывает процесс настройки и запуска бэкенд-части приложения, построенной на Spring Boot с интеграцией Keycloak для аутентификации и авторизации.

## Технологический стек

- Java 17
- Spring Boot 3.x
- Spring Security
- Keycloak Adapter
- Gradle

## Запуск в Docker

### Предварительные требования

- Docker установлен на вашей системе
- Keycloak запущен

### Сборка Docker-образа

Для сборки Docker-образа выполните следующую команду в директории `/backend`:

```bash
docker build -t kc_demo_backend_image .
```

### Запуск контейнера yf gjhne http://localhost:8888

Для запуска контейнера выполните:

```bash
docker run --name kc_demo_backend_container -d -p 8888:8888 kc_demo_backend_image
```

### Взаимодействие с другими контейнерами

Для взаимодействия с Keycloak и фронтендом, запущенными в других контейнерах, используется `host.docker.internal`, что позволяет контейнеру обращаться к сервисам, запущенным на хост-машине.

## API Endpoints

### Публичные эндпоинты

- `GET /api/public` - доступен без аутентификации
- `POST /api/auth/login` - аутентификация через Keycloak
- `POST /api/auth/logout` - выход из системы

### Приватные эндпоинты

- `GET /api/protected/resource` - доступен только аутентифицированным пользователям

## Конфигурация

Основные настройки приложения находятся в файле `src/main/resources/application.properties`.

### Важные параметры

```properties
# Порт приложения
server.port=8888

# Настройки Keycloak
keycloak.realm=demo_realm
keycloak.resource=demo_client
keycloak.auth-server-url=http://localhost:8080/
keycloak.ssl-required=external
keycloak.public-client=false
keycloak.credentials.secret=YOUR_CLIENT_SECRET
```

**Примечание**: Обязательно замените `YOUR_CLIENT_SECRET` на актуальный секретный ключ из настроек вашего клиента Keycloak.