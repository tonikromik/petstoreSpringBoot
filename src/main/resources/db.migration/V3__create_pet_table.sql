create table pet
(
    id          bigint auto_increment
        primary key,
    name        varchar(255) null,
    photo_url   varchar(255) null,
    status      varchar(255) null,
    category_id bigint       not null,
    tag_id      bigint       not null,
    constraint FK27he6ajeywvj8qjd8mmulygnl
        foreign key (category_id) references category (id),
    constraint FKfntq6duwwpcp23flydn51vybh
        foreign key (tag_id) references tag (id)
);

INSERT INTO petstore.pet (id, name, photo_url, status, category_id, tag_id) VALUES (1, 'Cat1', 'url1', 'AVAILABLE', 1, 1);
INSERT INTO petstore.pet (id, name, photo_url, status, category_id, tag_id) VALUES (2, 'Alisa', 'url2', 'PENDING', 1, 2);
INSERT INTO petstore.pet (id, name, photo_url, status, category_id, tag_id) VALUES (5, 'Dina', 'url12345', 'AVAILABLE', 2, 10);
INSERT INTO petstore.pet (id, name, photo_url, status, category_id, tag_id) VALUES (6, 'Nemo', 'url6', 'AVAILABLE', 3, 6);
INSERT INTO petstore.pet (id, name, photo_url, status, category_id, tag_id) VALUES (7, 'Cat3', 'url9', 'AVAILABLE', 1, 7);
INSERT INTO petstore.pet (id, name, photo_url, status, category_id, tag_id) VALUES (8, 'Cat15', 'url29', 'AVAILABLE', 1, 11);
INSERT INTO petstore.pet (id, name, photo_url, status, category_id, tag_id) VALUES (9, 'Cat25', 'url29', 'AVAILABLE', 1, 12);