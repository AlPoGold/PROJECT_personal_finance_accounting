CREATE TABLE IF NOT EXISTS expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10, 2) NOT NULL,
    category VARCHAR(255) NOT NULL,
    date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS incomes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10, 2) NOT NULL,
    source VARCHAR(255) NOT NULL,
    date DATE NOT NULL
);

CREATE TABLEIF NOT EXISTS balances (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_income DECIMAL(19, 2),
    total_expense DECIMAL(19, 2),
    total_balance DECIMAL(19, 2)
);


CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    birth_date DATE,
    email VARCHAR(255),
    role VARCHAR(255),
    balance_id BIGINT,
    FOREIGN KEY (balance_id) REFERENCES balances(id)
);



