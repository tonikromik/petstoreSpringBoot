create table orders
(
    id        bigint auto_increment
        primary key,
    complete  bit          null,
    pet_id    bigint       null,
    quantity  int          null,
    ship_date varchar(255) null,
    status    varchar(255) null
);

INSERT INTO petstore.orders (id, complete, pet_id, quantity, ship_date, status)
VALUES (1, true, 3, 2, '2023-03-26T11:06:36.796Z', 'PLACED');
INSERT INTO petstore.orders (id, complete, pet_id, quantity, ship_date, status)
VALUES (2, true, 1, 1, '2023-03-27T11:06:36.796Z', 'PLACED');
INSERT INTO petstore.orders (id, complete, pet_id, quantity, ship_date, status)
VALUES (3, true, 1, 1, '2023-03-27T11:06:36.796Z', 'PLACED');
INSERT INTO petstore.orders (id, complete, pet_id, quantity, ship_date, status)
VALUES (4, true, 5, 35, '2023-03-27T11:06:36.796Z', 'PLACED');