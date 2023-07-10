INSERT INTO users (id, email, first_name, last_name, password, phone, user_name, user_status, user_role)
VALUES (1, 'roman@gmail.com', 'Roman', 'Antonov', 'admin', '01', 'Roman', 1, 'ADMIN');
INSERT INTO users (id, email, first_name, last_name, password, phone, user_name, user_status, user_role)
VALUES (2, 'user2@gmail.com', 'seconduser', 'user', 'user', '02', 'user', 1, 'USER');
INSERT INTO users (id, email, first_name, last_name, password, phone, user_name, user_status, user_role)
VALUES (3, 'user3@gmail.com', 'thirduser', 'admin', 'admin', '03', 'admin', 1, 'ADMIN');
INSERT INTO users (id, email, first_name, last_name, password, phone, user_name, user_status, user_role)
VALUES (4, 'user4@gmail.com', '4user', 'lastuser', 'user', '01', 'user4', 1, 'USER');
INSERT INTO users (id, email, first_name, last_name, password, phone, user_name, user_status, user_role)
VALUES (5, 'user5@gmail.com', '5user', 'lastuser', 'user', '01', 'user5', 1, 'USER');
INSERT INTO users (id, email, first_name, last_name, password, phone, user_name, user_status, user_role)
VALUES (6, 'admin@gmail.com', 'Roman', 'Admin', 'admin', '04', 'Romik', 2, 'USER');
INSERT INTO users (id, email, first_name, last_name, password, phone, user_name, user_status, user_role)
VALUES (7, 'user7@gmail.com', 'userFirstname', 'userLastname', 'useruser', '012345', 'user7', 1, 'USER');

ALTER SEQUENCE users_seq RESTART WITH 8;

INSERT INTO categories (id, category_name) VALUES (1, 'cat');
INSERT INTO categories (id, category_name) VALUES (2, 'dog');
INSERT INTO categories (id, category_name) VALUES (3, 'fish');

ALTER SEQUENCE categories_seq RESTART WITH 4;

INSERT INTO pets (id, pet_name, pet_status, category_id) VALUES (1, 'Cat1', 'AVAILABLE', 1);
INSERT INTO pets (id, pet_name, pet_status, category_id) VALUES (2, 'Alisa', 'PENDING', 1);
INSERT INTO pets (id, pet_name, pet_status, category_id) VALUES (3, 'Dina', 'AVAILABLE', 2);
INSERT INTO pets (id, pet_name, pet_status, category_id) VALUES (4, 'Nemo', 'AVAILABLE', 3);
INSERT INTO pets (id, pet_name, pet_status, category_id) VALUES (5, 'Cat3', 'AVAILABLE', 1);
INSERT INTO pets (id, pet_name, pet_status, category_id) VALUES (6, 'Cat15', 'AVAILABLE', 1);
INSERT INTO pets (id, pet_name, pet_status, category_id) VALUES (7, 'Cat25', 'AVAILABLE', 1);

ALTER SEQUENCE pets_seq RESTART WITH 8;

INSERT INTO pet_urls (pet_id, photo_url) VALUES (1, 'url1');
INSERT INTO pet_urls (pet_id, photo_url) VALUES (1, 'url2');
INSERT INTO pet_urls (pet_id, photo_url) VALUES (2, 'url21');
INSERT INTO pet_urls (pet_id, photo_url) VALUES (3, 'url31');
INSERT INTO pet_urls (pet_id, photo_url) VALUES (4, 'url41');
INSERT INTO pet_urls (pet_id, photo_url) VALUES (4, 'url42');
INSERT INTO pet_urls (pet_id, photo_url) VALUES (5, 'url51');
INSERT INTO pet_urls (pet_id, photo_url) VALUES (6, 'url61');
INSERT INTO pet_urls (pet_id, photo_url) VALUES (7, 'url71');

INSERT INTO tags (id, tag_name) VALUES (1, 'aggressive');
INSERT INTO tags (id, tag_name) VALUES (2, 'calm');
INSERT INTO tags (id, tag_name) VALUES (3, 'evil');
INSERT INTO tags (id, tag_name) VALUES (4, 'wild');

ALTER SEQUENCE tags_seq RESTART WITH 5;

INSERT INTO pets_tags (pet_id, tag_id) VALUES (2, 2);
INSERT INTO pets_tags (pet_id, tag_id) VALUES (1, 4);
INSERT INTO pets_tags (pet_id, tag_id) VALUES (1, 3);
INSERT INTO pets_tags (pet_id, tag_id) VALUES (1, 1);
INSERT INTO pets_tags (pet_id, tag_id) VALUES (4, 1);

INSERT INTO orders (id, complete, pet_id, quantity, ship_date, order_status)
VALUES (1, true, 3, 2, '2023-03-26T11:06:36', 'PLACED');
INSERT INTO orders (id, complete, pet_id, quantity, ship_date, order_status)
VALUES (2, true, 1, 1, '2023-03-27T11:06:37', 'PLACED');
INSERT INTO orders (id, complete, pet_id, quantity, ship_date, order_status)
VALUES (3, true, 1, 1, '2023-03-27T11:06:38', 'PLACED');
INSERT INTO orders (id, complete, pet_id, quantity, ship_date, order_status)
VALUES (4, true, 5, 35, '2023-03-27T11:06:39', 'PLACED');

ALTER SEQUENCE orders_seq RESTART WITH 5;