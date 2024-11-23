На данный момент релизовано:
- Создание таблиц `migration_history` и `variables`, если они не существуют
- Блокировка БД перед запуском, разблокировка после использования
- Получение всех SQL-скриптов
- Получение всех ранее не использованных SQL-скриптов
- Выполнение всех ранее не использованных SQL-скриптов
- Юнит-тесты для некоторых классов
- Логирование по всему проекту

Инструкция по применению: 

Добавьте файл application.properties со следующими параметрами подключения:

Пример для PostgreSQL:

`db.username`=postgres

`db.password`=your_password

`db.url`=jdbc:postgresql://localhost:5432/your_db

`filepath`=your_directory_in_classpath

Пример для MYSQL:

`db.username`=root

`db.password`=your_password

`db.url`=jdbc:mysql://localhost:3306/test_db

`filepath`=your_directory_in_classpath

Пример для H2:

`db.username`=sa

`db.password`=

`db.url`=jdbc:h2:tcp://localhost/~/test

`filepath`=db/migration

Добавьте необходимые файлы миграции в classpath.

Он должны удовлетворять следующему шаблону:

`V_x.x.x...__your_description.sql`, где x - число.

В противном случае файл будет проигнорирован.
При запуске приложения все раннее не использованные версии выполнятся.

