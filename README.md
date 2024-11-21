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

`db.username`=postgres

`db.password`=your_password

`db.url`=jdbc:postgresql://localhost:5432/your_db

`filepath`=your_directory_in_classpath

Добавьте необходимые файлы миграции в classpath.

Он должны удовлетворять следующему шаблону:

`V_x.x.x...__your_description.sql`, где x - число.

В противном случае файл будет проигнорирован.
При запуске приложения все раннее не использованные версии выполнятся.

