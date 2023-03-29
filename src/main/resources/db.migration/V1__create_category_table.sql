create table category
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

INSERT INTO petstore.category (id, name) VALUES (1, 'cat');
INSERT INTO petstore.category (id, name) VALUES (2, 'dog');
INSERT INTO petstore.category (id, name) VALUES (3, 'fish');