DROP SCHEMA internetshop CASCADE;
CREATE SCHEMA internetshop;

CREATE TABLE internetshop.products
(
    product_id serial NOT NULL,
    name character varying(256) NOT NULL,
    price double precision NOT NULL,
    PRIMARY KEY (product_id)
);

ALTER TABLE internetshop.products
    OWNER to postgres;

CREATE TABLE internetshop.roles
(
    role_id serial NOT NULL,
    role_name character varying NOT NULL,
    PRIMARY KEY (role_id)
);

ALTER TABLE internetshop.roles
    OWNER to postgres;

CREATE TABLE internetshop.users
(
    user_id serial NOT NULL,
    name character varying(256),
    login character varying(256) NOT NULL,
    password character varying(256) NOT NULL,
    PRIMARY KEY (user_id)
);

ALTER TABLE internetshop.users
    OWNER to postgres;

CREATE TABLE internetshop.shopping_carts
(
    cart_id serial NOT NULL,
    "user_id" bigint NOT NULL,
    PRIMARY KEY (cart_id),
    FOREIGN KEY ("user_id")
        REFERENCES internetshop.users (user_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

ALTER TABLE internetshop.shopping_carts
    OWNER to postgres;

CREATE TABLE internetshop.shopping_cart_products
(
    cart_id bigint NOT NULL,
    product_id bigint NOT NULL,
    FOREIGN KEY (cart_id)
        REFERENCES internetshop.shopping_carts (cart_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (product_id)
        REFERENCES internetshop.products (product_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

ALTER TABLE internetshop.shopping_cart_products
    OWNER to postgres;

CREATE TABLE internetshop.orders
(
    order_id serial NOT NULL,
    user_id bigint NOT NULL,
    PRIMARY KEY (order_id),
    FOREIGN KEY (user_id)
        REFERENCES internetshop.users (user_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

ALTER TABLE internetshop.orders
    OWNER to postgres;

CREATE TABLE internetshop.orders_products
(
    order_id bigint NOT NULL,
    product_id bigint NOT NULL,
    FOREIGN KEY (order_id)
        REFERENCES internetshop.orders (order_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (product_id)
        REFERENCES internetshop.products (product_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

ALTER TABLE internetshop.orders_products
    OWNER to postgres;

CREATE TABLE internetshop.users_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES internetshop.users (user_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (role_id)
        REFERENCES internetshop.roles (role_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

ALTER TABLE internetshop.users_roles
    OWNER to postgres;

INSERT INTO internetshop.roles(role_name) VALUES ('ADMIN');
INSERT INTO internetshop.roles(role_name) VALUES ('USER');
