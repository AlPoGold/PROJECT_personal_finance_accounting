Удалить все таблицы


-- Отключаем временные ограничения на проверку внешних ключей
SET FOREIGN_KEY_CHECKS = 0;

-- Генерируем и выполняем запросы на удаление всех таблиц
SET @tables = NULL;
SELECT GROUP_CONCAT('`', table_name, '`') INTO @tables
FROM information_schema.tables
WHERE table_schema = 'financetracker';

SET @tables = IFNULL(@tables, 'dummy'); -- на случай если таблиц нет

SET @sql = CONCAT('DROP TABLE IF EXISTS ', @tables);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Включаем проверки внешних ключей обратно
SET FOREIGN_KEY_CHECKS = 1;