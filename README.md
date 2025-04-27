# Keycloak + Spring Boot + Vue Demo

Демонстрационный проект, показывающий интеграцию Keycloak с Spring Boot (бэкенд) и Vue.js (фронтенд).

## Архитектура проекта

Проект состоит из трех основных элементов:

1. **Keycloak** - сервер аутентификации и авторизации
2. **Spring Boot** - бэкенд-приложение, взаимодействующее с Keycloak
3. **Vue.js** - фронтенд-приложение, взаимодействующее с бэкендом

### Схема взаимодействия

```
+-------------+      +---------------+      +------------+
|             |      |               |      |            |
|  Frontend   +----->+    Backend    +----->+  Keycloak  |
|  (Vue.js)   |      | (Spring Boot) |      |            |
|             |      |               |      |            |
+-------------+      +---------------+      +------------+
```

## Запуск в Docker

Все компоненты проекта можно запустить в Docker-контейнерах.

### 1. Запуск Keycloak

```bash
# Запуск Keycloak в Docker
docker run --name kc_demo -d -p 8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.1.4 start-dev
```

После запуска Keycloak необходимо минимально настроить сервер:
(p.s. визуальная инструкция здесь: [keycloak/README.md](keycloak/README.md))

1. Создать новый realm (`demo_realm`)
2. Создать клиент (`demo_client`) с настройками:
- Client authentication: `On`
- Authentication flow: `off`
- Direct access grants: `On`
3. **Важно**: Скопировать секретный ключ клиента из настроек Keycloak и обновить его в файле `backend/src/main/resources/application.properties` в переменной `keycloak.credentials.secret`
4. Создать пользователя (`demo_user`)
**Важно**: для пользователя необходимо заполнить все поля:
"Username", "Email", "First name", "Last name"
5. Задать пользователю пароль (`1234`, temporary: `off`)

### 2. Запуск бэкенда

Подробные инструкции по запуску бэкенда находятся в файле [backend/README.md](backend/README.md).

```bash
# Сборка образа в директории ./backend
docker build -t kc_demo_backend_image . 

# Запуск контейнера
docker run --name kc_demo_backend_container -d -p 8888:8888 kc_demo_backend_image
```

### 3. Запуск фронтенда

Подробные инструкции по запуску фронтенда находятся в файле [frontend/README.md](frontend/README.md).

```bash
# Сборка образа в директории ./frontend
docker build -t kc_demo_frontend_image .

# Запуск контейнера
docker run --name kc_demo_frontend_container -d -p 5173:80 kc_demo_frontend_image
```
