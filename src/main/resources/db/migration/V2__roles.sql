CREATE TABLE permissions (
    id SERIAL PRIMARY KEY,
    permission VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE roles (
    role_id SERIAL PRIMARY KEY,
    role VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE roles_permissions (
    role_id INTEGER REFERENCES roles,
    permission_id INTEGER REFERENCES permissions,
    PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE user_info (
    info_id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(127),
    last_name VARCHAR(127),
    mid_name VARCHAR(127),
    email VARCHAR(320)
);

CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(127) NOT NULL,
    password VARCHAR(127) NOT NULL,
    info_id BIGINT REFERENCES user_info
);

CREATE TABLE users_roles (
    user_id BIGINT REFERENCES users,
    role_id INTEGER REFERENCES roles,
    PRIMARY KEY (user_id, role_id)
)