CREATE TABLE internetshop.products
(
    product_id serial NOT NULL,
    name character varying(225) NOT NULL,
    price double precision NOT NULL,
    PRIMARY KEY (product_id)
);

ALTER TABLE internetshop.products
    OWNER to postgres;