-- Таблица с пользователями
DROP TABLE IF EXISTS users;
CREATE TABLE users (
                       id BIGINT PRIMARY KEY,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       age INT,
                       active BOOLEAN
);
