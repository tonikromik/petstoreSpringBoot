CREATE SCHEMA IF NOT EXISTS test;

CREATE TABLE IF NOT EXISTS users
(
    id          BIGINT       NOT NULL PRIMARY KEY,
    email       VARCHAR(255) NOT NULL UNIQUE,
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    password    VARCHAR(255) NOT NULL,
    phone       VARCHAR(255),
    user_name   VARCHAR(255) NOT NULL UNIQUE,
    user_status INT          NOT NULL,
    user_role   VARCHAR(255)
);

CREATE SEQUENCE IF NOT EXISTS users_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS categories
(
    id            BIGINT       NOT NULL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS categories_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS pets
(
    id          BIGINT       NOT NULL PRIMARY KEY,
    pet_name    VARCHAR(255) NOT NULL,
    pet_status  VARCHAR(255) NOT NULL,
    category_id BIGINT       NOT NULL,
    CONSTRAINT pets_categories_fk FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE INDEX IF NOT EXISTS pet_status_idx ON pets(pet_status);


CREATE SEQUENCE IF NOT EXISTS pets_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS tags
(
    id       BIGINT       NOT NULL PRIMARY KEY,
    tag_name VARCHAR(255) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS tags_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS orders
(
    id           BIGINT       NOT NULL PRIMARY KEY,
    complete     BIT          NOT NULL,
    pet_id       BIGINT       NOT NULL,
    quantity     INT          NOT NULL,
    ship_date    DATETIME(6)  NOT NULL,
    order_status VARCHAR(255) NOT NULL,
    CONSTRAINT pets_orders_fk FOREIGN KEY (pet_id) REFERENCES pets (id)
);

CREATE SEQUENCE IF NOT EXISTS orders_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS pets_tags
(
    tag_id BIGINT NOT NULL,
    pet_id BIGINT NOT NULL,
    CONSTRAINT tag_id_fk FOREIGN KEY (tag_id) REFERENCES tags (id),
    CONSTRAINT pet_id_fk FOREIGN KEY (pet_id) REFERENCES pets (id)
);

CREATE TABLE IF NOT EXISTS pet_urls
(
    pet_id    BIGINT NOT NULL,
    photo_url VARCHAR(255),
    CONSTRAINT pet_urls_fk FOREIGN KEY (pet_id) REFERENCES pets (id)
);