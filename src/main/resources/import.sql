-- INSERT INTO products VALUES ('Bread', 1.50), ('Butter', 2.65), ('Milk', 1.12), ('Cheese', 5.15), ('Carrots', 0.45);
CREATE TABLE IF NOT EXISTS web_store.products(id bigint GENERATED ALWAYS AS IDENTITY (START 1000 INCREMENT 1), title character varying(255), cost double precision, PRIMARY KEY (id));
ALTER TABLE web_store.products OWNER to postgres;