INSERT INTO order_details (address, phone) VALUES
    ('address 1', '+7-123-4567890'),
    ('address 2', '+7-123-4567890'),
    ('address 3', '+7-123-4567890'),
    ('address 4', '+7-123-4567890');

INSERT INTO orders (user_id, details_id, total, status_code)  VALUES
    (1, 1, 123.45, 600),
    (2, 2, 444.44, 600),
    (3, 3, 555.55, 600),
    (2, 4, 100.00, 600);

INSERT INTO order_items (order_id, product_id, quantity, product_price, item_price) VALUES
    (1, 1, 2, 12.00, 24.0),
    (1, 2, 1, 20.00, 20.0),
    (1, 3, 1, 30.00, 30.0),
    (2, 1, 1, 12.00, 12.0),
    (2, 3, 1, 30.00, 30.0),
    (3, 2, 1, 55.00, 55.0),
    (4, 2, 2, 20.00, 40.0);

CREATE INDEX idx_orders_users ON orders (user_id);
CREATE INDEX idx_order_items_product_order ON order_items (product_id, order_id);