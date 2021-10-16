CREATE TABLE order_details (
    details_id BIGSERIAL PRIMARY KEY,
    address VARCHAR(512),
    phone VARCHAR(15),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items (
    item_id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL REFERENCES products(id),
    quantity INTEGER,
    product_price DECIMAL(12,2),
    item_price DECIMAL(12,2),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    order_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(user_id),
    details_id BIGINT NOT NULL REFERENCES order_details(details_id),
    total DECIMAL(12,2),
    status_code INTEGER NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);