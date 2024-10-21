Description:
The personal financial accounting system is designed to manage users' personal finances, 
including tracking income and expenses, planning a budget and achieving financial goals. 
It provides users with the ability to analyze their financial transactions, 
manage their budgets, and make informed financial decisions.



Demo user: 
email: diplom@mail.com
password: diplom


Launch the application:
1) IDE: PersonalFinanceAccountingApplication.java, localhost:8080
2) from Docker: docker-compose.yml, browser path: localhost:9091


ATTENTION! For deleting all rows in each table you need use this script 


SET FOREIGN_KEY_CHECKS = 0;
SET @tables = NULL;
SELECT GROUP_CONCAT('`', table_name, '`') INTO @tables
FROM information_schema.tables
WHERE table_schema = 'financetracker';
SET @tables = IFNULL(@tables, 'dummy');
SET @sql = CONCAT('DROP TABLE IF EXISTS ', @tables);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
SET FOREIGN_KEY_CHECKS = 1;