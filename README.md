# BookShelf

Багатомодульний Maven-проект веб-застосунку на Spring Boot з PostgreSQL, Flyway, Docker та підтримкою деплою на Render.

---

## Структура проекту

| Модуль        | Опис                                                                 |
| ------------- | -------------------------------------------------------------------- |
| `core`        | Доменні моделі, DTO та контракти сервісів                            |
| `persistence` | Spring Data JPA репозиторії, Flyway міграції, імплементації сервісів |
| `web`         | Spring MVC (Thymeleaf), Spring Security, REST API, Actuator          |

---

## Швидкий старт

### Вимоги

- Java 21+
- Docker & Docker Compose
- Maven 3.9+ (опціонально, є wrapper)

### Запуск через Docker Compose

```bash
docker compose up -d
```

Це підніме:

- PostgreSQL 16 на порту `5432`
- Веб-додаток на порту `8080`

### Доступ

- **Головна:** http://localhost:8080
- **Health:** http://localhost:8080/actuator/health

### Обліковий запис адміна

| Поле   | Значення   |
| ------ | ---------- |
| Логін  | `admin`    |
| Пароль | `admin123` |

---

## Конфігурація

### Змінні оточення

| Змінна                       | За замовчуванням                            | Опис             |
| ---------------------------- | ------------------------------------------- | ---------------- |
| `SPRING_DATASOURCE_URL`      | `jdbc:postgresql://postgres:5432/bookshelf` | URL бази даних   |
| `SPRING_DATASOURCE_USERNAME` | `bookshelf_user`                            | Користувач БД    |
| `SPRING_DATASOURCE_PASSWORD` | `bookshelf_pass`                            | Пароль БД        |
| `PORT`                       | `8080`                                      | Порт веб-сервера |
| `APP_ADMIN_USERNAME`         | `admin`                                     | Логін адміна     |
| `APP_ADMIN_PASSWORD`         | `admin123`                                  | Пароль адміна    |

---

## Docker

### Збірка вручну

```bash
docker build -t bookshelf-web -f web/Dockerfile .
```

### Запуск окремо

```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/bookshelf \
  -e SPRING_DATASOURCE_USERNAME=bookshelf_user \
  -e SPRING_DATASOURCE_PASSWORD=bookshelf_pass \
  bookshelf-web
```

---

## Деплой на Render

1. **Створіть PostgreSQL сервіс** на Render
2. **Створіть Web Service:**
   - Підключіть GitHub репозиторій
   - Environment: `Docker`
   - Dockerfile path: `web/Dockerfile`
3. **Налаштуйте змінні оточення:**
   - `SPRING_DATASOURCE_URL` — Internal Database URL
   - `SPRING_DATASOURCE_USERNAME` — DB username
   - `SPRING_DATASOURCE_PASSWORD` — DB password
4. **Перевірте логи** — Flyway міграції мають застосуватися

---

## Функціонал

- Реєстрація та авторизація користувачів
- Каталог книг з описами
- Коментарі до книг
- Адмін-панель для керування книгами
- REST API для інтеграцій
- Health checks для моніторингу

---

## Розробка

### Локальний запуск без Docker

1. Запустіть PostgreSQL:

   - База: `bookshelf`
   - Користувач: `bookshelf_user`
   - Пароль: `bookshelf_pass`

2. Зберіть та запустіть:

```bash
mvn clean package -pl web -am -DskipTests
java -jar web/target/web-*.jar
```

### Зупинка Docker

```bash
docker compose down
```

### Повне очищення (з видаленням даних)

```bash
docker compose down -v
```
