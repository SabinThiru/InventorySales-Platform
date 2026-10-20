-- Inventory & Sales Management System
-- Original starter records. Run after schema.sql.

USE
inventory_sales_db;

INSERT INTO products (name, category, price, stock_quantity)
VALUES ('Mechanical Keyboard', 'Electronics', 89.99, 15),
       ('Wireless Mouse', 'Electronics', 29.99, 30),
       ('USB-C Cable', 'Accessories', 9.99, 50),
       ('Laptop Stand', 'Office', 39.99, 20),
       ('Notebook', 'Stationery', 4.99, 100);

INSERT INTO customers (first_name, last_name, email)
VALUES ('John', 'Smith', 'john.smith@email.com'),
       ('Sarah', 'Johnson', 'sarah.j@email.com'),
       ('Michael', 'Brown', 'michael.b@email.com');

-- Sales and sale_items are populated by the Java application
-- when sales are created.
