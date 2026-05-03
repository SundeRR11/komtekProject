# Medical Information System

## Описание приложения

Сервис для управления заявками на медицинские исследования. Позволяет создавать заявки для пациентов, выполнять поиск по различным параметрам и управлять статусами заявок.

### Основной функционал

- Управление заявками (Order)
  - Создание заявки на исследование
  - Получение заявки по ID
  - Поиск заявок по критериям: id, ФИО+дата рождения, СНИЛС, ЕНП, статус

- Управление пациентами (Patient)
  - Получение данных пациента по ID
  - Хранение СНИЛС
  - Хранение полиса ОМС (отдельная таблица)
 
### Технологический стек

| Технология | Версия | Назначение |
|------------|--------|------------|
| Java | 26 | Язык программирования |
| Spring Boot | 4.0.5 | Основной фреймворк |
| Spring MVC | - | REST API контроллеры |
| Spring Data JPA | - | Работа с базой данных |
| PostgreSQL | 17.6 | Реляционная база данных |
| Liquibase | 5.0.2 | Миграции базы данных |
| Maven | 3.8 | Сборка проекта |

## Сборка и развертывание

### Требования

- Java 26 
- Maven 3.8+
- PostgreSQL 15+
- Git

### Клонирование репозитория

```bash
git clone https://github.com/SundeRR11/komtekProject.git
cd komtekProject
```
### Настройка базы данных
1. Установите PostgreSQL
2. Создайте базу данных:

```sql
CREATE DATABASE komtek_db;
```
3. Настройте подключение в application.yml:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/komtek_db
    username: postgres
    password: your_password
```

### Сборка проекта
```bash
mvn clean package
```

### Запуск приложения
```bash
mvn spring-boot:run
```

### API Эндпоинты
## Заявки (Orders)
| Метод | Эндпоинт | Описание |
|------|--------------|------------|
|POST| `/api/v1/orders`|	Создание заявки|
|GET|	`/api/v1/orders/{id}`|	Получение заявки по ID|
|GET|	`/api/v1/orders/search/by-id/{id}`|	Поиск по ID заявки|
|GET|	`/api/v1/orders/search/by-fullname-and-birthdate?fullName=&birthDate=`|	Поиск по ФИО+дата рождения|
|GET|	`/api/v1/orders/search/by-snils/{snils}`|	Поиск по СНИЛС пациента|
|GET|	`/api/v1/orders/search/by-enp/{enp}`|	Поиск по ЕНП пациента|
|GET|	`/api/v1/orders/search/by-status/{status}`|	Поиск по статусу|
| POST | `/api/v1/orders/search` | **Универсальный поиск** (по любым параметрам) |

## Пациенты (Patients)
| Метод | Эндпоинт | Описание |
|------------|--------|------------|
|GET|	`/api/v1/patients/{id}`|	Получение пациента по ID|
|GET|	`/api/v1/patients/{id}/policy`|	Получение полиса пациента|
|POST|	`/api/v1/patients/{id}/policy?policyNumber=`|	Добавление полиса пациенту|

### Коды ошибок
Все ошибки возвращаются в едином формате:

```json
{
    "errors": [
        {
            "code": "ERROR_CODE",
            "message": "Описание ошибки"
        }
    ]
}
```

### Коды ошибок
|Код|	Описание	|HTTP статус|
|--------------|------------|--------|
|`PATIENT_NOT_FOUND`|	Пациент с указанным ID не найден|	400 Bad Request|
|`ORDER_NOT_FOUND`|	Заявка с указанным ID не найдена|	400 Bad Request|
|`VALIDATION_ERROR`|	Ошибка валидации входных данных|	400 Bad Request|
|`TYPE_MISMATCH`|	Неверный тип параметра запроса|	400 Bad Request|
|`INVALID_JSON`|	Неверный формат JSON|	400 Bad Request|
|`DUPLICATE_KEY`|	Запись с такими данными уже существует|	409 Conflict|
|`INTERNAL_ERROR`|	Внутренняя ошибка сервера|	500 Internal Server Error|

### Статусы заявок
|Статус	|Описание|
|--------|----------------|
|`REGISTERED`|	Зарегистрирована (начальный статус при создании)|
|`IN_PROGRESS`|	В процессе выполнения|
|`COMPLETED`|	Завершена|
|`CANCELED`|	Отменена|



### Контакты

Разработчик: [Александр]

GitHub: [[ссылка на репозиторий](https://github.com/SundeRR11/komtekProject)]




