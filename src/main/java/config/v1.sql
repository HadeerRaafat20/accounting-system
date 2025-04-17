CREATE TABLE roles (
    id BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE user_roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    role_id BIGINT,
    UNIQUE (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (role_id) REFERENCES role_entity(id)
);

CREATE TABLE invoice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_time DATETIME,
    provider_name VARCHAR(255),
    address VARCHAR(255),
    total DOUBLE,
    paid DOUBLE,
    remaining DOUBLE,
    delivered_by VARCHAR(255)
);

CREATE TABLE invoice_line (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255),
    quantity INT,
    price DOUBLE,
    value DOUBLE,
    invoice_id BIGINT,
    CONSTRAINT fk_invoice
        FOREIGN KEY (invoice_id) REFERENCES invoice(id)
        ON DELETE CASCADE
);

CREATE TABLE providers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    service VARCHAR(100),
    note TEXT
);

-- Seed roles
INSERT INTO roles (id, name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_CHEF_ACCOUNTANT'),
(3, 'ROLE_ACCOUNTANT');
