# Guestbook WebApp

---

## Версії

- **JDK:** 15
- **Maven:** mvn 3.9.x
- **Jetty Maven Plugin:** 11.0.20
- **H2 Database:** 2.2.224

---

## Команди запуску

```bash
# Компіляція проекту:
mvn clean compile

# Запуск вбудованого Jetty:
mvn jetty:run

# Зупинка сервера:
Ctrl + C
```

---

## Налаштування бази даних H2 (файл)

- **URL**: jdbc:h2:file:./data/guest;AUTO_SERVER=TRUE
- **Файл**: ./data/guest.mv.db (відносно кореня проекту)
- **Користувач**: sa

---

## Ендпоїнти

| Метод | URL      | Параметри                  | Опис                   |
|-------| ---------|----------------------------|------------------------|
| POST  |/comments | author (≤64), text (≤1000) | Додає новий коментар   |
| GET   |/comments | -                          | Повертає всі коментарі | 
