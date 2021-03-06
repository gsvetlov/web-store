CREATE TABLE products
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(127) NOT NULL,
    cost DECIMAL(12,2) NOT NULL
);
INSERT INTO products (title, cost) VALUES ('Хлеб', 62.50),
                                          ('Молоко', 510.20),
                                          ('Сыр', 314.22),
                                          ('Картофель', 114.57),
                                          ('Морковь', 113.31),
                                          ('Лимон', 745.37),
                                          ('Апельсин', 76.69),
                                          ('Яблоко', 234.98),
                                          ('Огурец', 452.68),
                                          ('Помидор', 723.29),
                                          ('Миндаль', 153.03),
                                          ('Фундук', 675.18),
                                          ('Яйцо', 223.21),
                                          ('Треска', 227.29),
                                          ('Окунь', 476.73),
                                          ('Лук', 91.68),
                                          ('Чеснок', 130.56),
                                          ('Сметана', 322.30),
                                          ('Творог', 145.40),
                                          ('Перец', 113.00);