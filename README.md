

Демонстрационный пользователь: 
email: diplom@mail.com
password: diplom


Запуск приложения:
1) из IDE: PersonalFinanceAccountingApplication.java, в браузере на localhost:8080
2) из контейнера: запуск docker-compose.yml, в браузере на localhost:9091


ATTENTION! Если нужно удалить все записи и все таблицы, необходимо выполнить в консоли 
базы данных скрипт, написанный ниже!


SET FOREIGN_KEY_CHECKS = 0;
SET @tables = NULL;
SELECT GROUP_CONCAT('`', table_name, '`') INTO @tables
FROM information_schema.tables
WHERE table_schema = 'financetracker';
SET @tables = IFNULL(@tables, 'dummy'); -- на случай если таблиц нет
SET @sql = CONCAT('DROP TABLE IF EXISTS ', @tables);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
SET FOREIGN_KEY_CHECKS = 1;