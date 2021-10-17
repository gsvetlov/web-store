CREATE TABLE comments (
    comment_id BIGSERIAL PRIMARY KEY,
    content VARCHAR(256),
    user_id BIGINT NOT NULL REFERENCES users(user_id),
    product_id BIGINT NOT NULL REFERENCES products(id),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_comments_product ON comments (product_id ASC);

INSERT INTO comments (content, user_id, product_id, created_on, updated_on) VALUES
    ('comment 1', 1, 1, '2021-01-01', '2021-01-01'),
    ('comment 2', 1, 2, '2021-01-01', '2021-01-01'),
    ('comment 3', 1, 3, '2021-01-01', '2021-01-01'),
    ('comment 4', 2, 1, '2021-01-02', '2021-01-02'),
    ('comment 5', 2, 3, '2021-01-03', '2021-01-03'),
    ('comment 6', 3, 2, '2021-01-02', '2021-01-02'),
    ('comment 7', 2, 2, '2021-01-04', '2021-01-04');