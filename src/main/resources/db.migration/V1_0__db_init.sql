create table if not exists users
(
    id          bigint       not null primary key,
    email       varchar(255) not null,
    first_name  varchar(255),
    last_name   varchar(255),
    password    varchar(255) not null,
    phone       varchar(255),
    user_name   varchar(255) not null unique,
    user_status int          not null
);

create table users_seq (next_val bigint);
insert into users_seq value ( 1 );

create table if not exists categories
(
    id            bigint       not null primary key,
    category_name varchar(255) not null
);

create table categories_seq (next_val bigint);
insert into categories_seq value ( 1 );

create table if not exists pets
(
    id          bigint       not null primary key,
    pet_name    varchar(255) not null,
#     photo_url   varchar(255) not null,
    pet_status  varchar(255) not null,
    category_id bigint       not null,
    constraint pets_categories_fk
        foreign key (category_id) references categories (id)
);

create table pets_seq (next_val bigint);
insert into pets_seq value ( 1 );

create table if not exists tags
(
    id       bigint       not null  primary key,
    tag_name varchar(255) not null
);

create table tags_seq (next_val bigint);
insert into tags_seq value ( 1 );

create table if not exists orders
(
    id           bigint       not null primary key,
    complete     bit          not null,
    pet_id       bigint       not null,
    quantity     int          not null,
    ship_date    varchar(255) not null,
    order_status varchar(255) not null
);

create table orders_seq (next_val bigint);
insert into orders_seq value ( 1 );

create table pets_tags
(
    tag_id bigint not null,
    pet_id bigint not null,
    constraint tag_id_fk foreign key (tag_id) references tags (id),
    constraint pet_id_fk foreign key (pet_id) references pets (id)
);

create table pet_urls
(
    pet_id bigint not null,
    photo_url varchar(255),
    constraint pet_urls_fk foreign key (pet_id) references pets (id)
);
