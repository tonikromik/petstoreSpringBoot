create table IF NOT EXISTS tag
(
    id       bigint auto_increment
        primary key,
    tag_name varchar(255) null
);

INSERT INTO petstore.tag (id, tag_name) VALUES (1, 'Tag1');
INSERT INTO petstore.tag (id, tag_name) VALUES (2, 'Tag2');
INSERT INTO petstore.tag (id, tag_name) VALUES (3, 'Tag3');
INSERT INTO petstore.tag (id, tag_name) VALUES (4, 'Tag4');
INSERT INTO petstore.tag (id, tag_name) VALUES (5, 'Tag5');
INSERT INTO petstore.tag (id, tag_name) VALUES (6, 'Tag6');
INSERT INTO petstore.tag (id, tag_name) VALUES (7, 'Tag17');
INSERT INTO petstore.tag (id, tag_name) VALUES (8, 'Tag21');
INSERT INTO petstore.tag (id, tag_name) VALUES (9, 'Tag21');
INSERT INTO petstore.tag (id, tag_name) VALUES (10, 'Tag21');
INSERT INTO petstore.tag (id, tag_name) VALUES (11, 'Tag175');
INSERT INTO petstore.tag (id, tag_name) VALUES (12, 'Tag175');