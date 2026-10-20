-- Inventory & Sales Management System
-- Create the database and its four tables.

CREATE DATABASE IF NOT EXISTS InventoryApp;

USE InventoryApp;

CREATE TABLE products
(
    product_id     INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100)   NOT NULL,
    category       VARCHAR(100),
    price          DECIMAL(10, 2) NOT NULL,
    stock_quantity INT            NOT NULL
);

CREATE TABLE customers
(
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(150)
);

CREATE TABLE sales
(
    sale_id      INT AUTO_INCREMENT PRIMARY KEY,
    customer_id  INT,
    sale_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2),
    FOREIGN KEY (customer_id)
        REFERENCES customers (customer_id)
);

CREATE TABLE sale_items
(
    sale_item_id INT AUTO_INCREMENT PRIMARY KEY,
    sale_id      INT,
    product_id   INT,
    quantity     INT            NOT NULL,
    price_each   DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (sale_id)
        REFERENCES sales (sale_id),
    FOREIGN KEY (product_id)
        REFERENCES products (product_id)
);
