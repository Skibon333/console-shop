CREATE DATABASE console_shop
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE categories (
    id   SERIAL,
    name VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE products (
    id            SERIAL,
    name     	  VARCHAR(45) NOT NULL,
    description   VARCHAR(45) NOT NULL,
    price         DECIMAL(10) NOT NULL,
    manufacturer  VARCHAR(45) NOT NULL,
    category_id   INTEGER     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT category_product_id
    FOREIGN KEY (category_id)
    REFERENCES categories (id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
    );

INSERT INTO categories (name)
VALUES
    ('Laptop'),
    ('Tablet'),
    ('Smartphone');

INSERT INTO products (name, description, price, manufacturer, category_id)
VALUES
    ('Laptop', 'ASUS ROG 751', 1100, 'China', 1),
    ('Laptop', 'ASUS ROG 750', 1300, 'China', 1),
    ('Laptop', 'ASUS ROG 756', 1200, 'China', 1),
    ('Laptop', 'ASUS ROG 755', 900, 'China', 1),
    ('Laptop', 'ASUS ROG 754', 999, 'China', 1),
    ('Laptop', 'ASUS ROG 952', 1500, 'China', 1),
    ('Tablet', 'ASUS ZenPad 751', 800, 'China', 2),
    ('Tablet', 'IPad', 700, 'China', 2),
    ('Tablet', 'Samsung', 900, 'China', 2),
    ('Smartphone', 'Samsung Galaxy', 600, 'South Korea', 3),
    ('Smartphone', 'Xiaomi', 400, 'China', 3),
    ('Smartphone', 'IPhone', 900, 'USA', 3);