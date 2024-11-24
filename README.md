# **Migration Tool**

---

## **Описание**
Библиотека для автоматического управления версиями баз данных (миграции). Поддерживает работу с `MySQL`, `PostgreSQL`, и `H2 Database`.  
Реализовано:
- Поддержка трех разных баз данных: PostgreSQL, MYSQL, H2 Database
- Создание таблиц migration_history и variables, если они не существуют
- Блокировка БД перед запуском, разблокировка после использования
- Получение всех SQL-скриптов
- Получение всех ранее не использованных SQL-скриптов
- Выполнение всех ранее не использованных SQL-скриптов
- Юнит-тесты для публичных методом
- Логирование по всему проекту

---

## **Функциональность**
- **Поддержка трех баз данных**: `PostgreSQL`, `MySQL`, `H2 Database`.  
- **Автоматическое создание таблиц**:
  - `migration_history` — для хранения выполненных миграций.
  - `variables` — для блокировки/разблокировки базы данных.
- **Команды**:
  - `migrate` — выполняет все ранее неиспользованные миграции.
  - `status` — отображает текущий статус базы данных (актуальная версия).
  
---

## **Начало работы**

### **1. Подключение библиотеки**
#### **Gradle**
Добавьте в зависимости:

`gradle` Добавьте в buld.gradle:

```
implementation files('путь-к-скачанному-jar-файлу/MigrationTool-1.0-SNAPSHOT-all.jar')
```

`maven` Добавьте в pom.xml:
```
<dependencies>
    <dependency>
        <groupId>innowise.internship</groupId>
        <artifactId>MigrationTool</artifactId>
        <version>1.0-SNAPSHOT</version>
        <scope>system</scope>
        <systemPath>путь-к-скачанному-файлу/MigrationTool-1.0-SNAPSHOT-all.jar</systemPath>
    </dependency>
</dependencies>
```
---
### **2. Настройка файлов миграций**

Создайте директорию для миграций в ресурсах проекта и добавьте `.sql` файлы.
Формат имени файлов миграций:
```
V_x.x.x...__create_users_table.sql
#x - число
```
Важно:
- Файлы, не соответствующие шаблону, будут проигнорированы.
- Если один из скриптов содержит синтаксическую ошибку, выполнение миграции будет отменено.
---
### **3. Настройка application.properties**

Основные параметры:

```
# Путь к файлам миграций
filepath=db/migration

# Команда для выполнения
command=migrate # или status
```

Подключение к базам данных.

PostgreSQL:
```
db.username=postgres
db.password=your_password
db.url=jdbc:postgresql://localhost:5432/your_db
```

MySQL:
```
db.username=root
db.password=your_password
db.url=jdbc:mysql://localhost:3306/your_db
```
H2 Database:
```
db.username=sa
db.password=your_password
jdbc:h2:tcp://localhost/~/your_db
```
---

### **4. Использование**
Для запуска библиотеки вызовите у статического класса MigrationTool метод run():
```
MigrationTool.run();
```
---

### **Зависимости**

Библиотека включает следующие зависимости:
- Тестирование 
```
testImplementation platform('org.junit:junit-bom:5.10.0')
testImplementation 'org.junit.jupiter:junit-jupiter'
testImplementation 'org.mockito:mockito-core:5.12.0'
```
- Драйвера баз данных:
```
implementation 'com.h2database:h2:2.2.224'
implementation 'org.postgresql:postgresql:42.7.4'
implementation 'com.mysql:mysql-connector-j:9.1.0'
```
- Lombok
```
compileOnly 'org.projectlombok:lombok:1.18.36'
annotationProcessor 'org.projectlombok:lombok:1.18.36'
```
- Логирование:
```
implementation 'ch.qos.logback:logback-core:1.5.12'
implementation 'ch.qos.logback:logback-classic:1.5.12'

```

