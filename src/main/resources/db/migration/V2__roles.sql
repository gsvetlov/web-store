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
    username VARCHAR(127) NOT NULL UNIQUE,
    password VARCHAR(127) NOT NULL,
    info_id BIGINT REFERENCES user_info
);

CREATE TABLE users_roles (
    user_id BIGINT REFERENCES users,
    role_id INTEGER REFERENCES roles,
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO roles (role) values ('ROLE_SA'), ('ROLE_ADMIN'), ('ROLE_USER');
INSERT INTO permissions (permission) values ('grant-all-permissions'),
                                            ('edit-role-permission'),
                                            ('edit-permission'),
                                            ('edit-user-role'),
                                            ('edit-user-info');
INSERT INTO roles_permissions values (1, 1),
                                     (1, 2),
                                     (1, 3),
                                     (1, 4),
                                     (1, 5),
                                     (2, 4),
                                     (2, 5);

-- sa:root '$2a$12$ofGl8wIqvoqlU2McOmMdjOp1ziqFdLSOYo075hxbBfPYkX0JNr5B.'
-- admin:admin '$2a$12$Izsq7LbaEg28ZPwi4pkyxuGJboPFs0Ga62Fr3hudNQPKoYA/YyASW'
-- bob:1234 '$2a$12$nJoqSubKOmtod59CyjdoU.DcaE9AH7eatWQBEkn8bPjUR5RMSDR/y'
-- jack:1234 '$2a$12$a.yWBZhmbVwfVqs5GuF8UehE7cicBqxxDrJkbwWSA.NiRtCm1jzxm'

INSERT INTO users (username, password) VALUES ('sa', '$2a$12$ofGl8wIqvoqlU2McOmMdjOp1ziqFdLSOYo075hxbBfPYkX0JNr5B.'),
                                              ('admin', '$2a$12$Izsq7LbaEg28ZPwi4pkyxuGJboPFs0Ga62Fr3hudNQPKoYA/YyASW'),
                                              ('bob', '$2a$12$nJoqSubKOmtod59CyjdoU.DcaE9AH7eatWQBEkn8bPjUR5RMSDR/y'),
                                              ('jack', '$2a$12$a.yWBZhmbVwfVqs5GuF8UehE7cicBqxxDrJkbwWSA.NiRtCm1jzxm');
INSERT INTO users_roles values (1, 1),
                               (2, 2),
                               (3, 2),
                               (3, 3),
                               (4, 3);