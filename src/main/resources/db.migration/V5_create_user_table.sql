create table user
(
    id          bigint auto_increment
        primary key,
    email       varchar(255) null,
    first_name  varchar(255) null,
    last_name   varchar(255) null,
    password    varchar(255) null,
    phone       varchar(255) null,
    user_name   varchar(255) null,
    user_status int          null,
    constraint UK_lqjrcobrh9jc8wpcar64q1bfh
        unique (user_name)
);

INSERT INTO petstore.user (id, email, first_name, last_name, password, phone, user_name, user_status) VALUES (1, 'roman@gmail.com', 'Roman', 'Antonov', 'admin', '01', 'Roman', 1);
INSERT INTO petstore.user (id, email, first_name, last_name, password, phone, user_name, user_status) VALUES (2, 'user@gmail.com', 'firstuser', 'lastuser', 'user', '01', 'user2', 1);
INSERT INTO petstore.user (id, email, first_name, last_name, password, phone, user_name, user_status) VALUES (5, 'user@gmail.com', 'firstuser', 'lastuser', 'user', '01', 'user3', 1);
INSERT INTO petstore.user (id, email, first_name, last_name, password, phone, user_name, user_status) VALUES (7, 'user@gmail.com', 'firstuser', 'lastuser', 'user', '01', 'user4', 1);
INSERT INTO petstore.user (id, email, first_name, last_name, password, phone, user_name, user_status) VALUES (8, 'user@gmail.com', 'firstuser', 'lastuser', 'user', '01', 'user5', 1);
INSERT INTO petstore.user (id, email, first_name, last_name, password, phone, user_name, user_status) VALUES (10, 'admin@gmail.com', 'Roman', 'Admin', 'admin', '04', 'Romik', 2);
INSERT INTO petstore.user (id, email, first_name, last_name, password, phone, user_name, user_status) VALUES (11, 'userasdasd@gmail.com', 'userFirstname', 'userLastname', 'useruser', '012345', 'user25', 1);