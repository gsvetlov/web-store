--роли
create table roles
(
    role_id serial primary key,
    role    varchar(255) not null unique
);
-- пользователи
create table users
(
    user_id  bigserial primary key,
    username varchar(255) not null unique,
    password varchar(255) not null,
    enabled  bool,
    name     varchar(255) not null,
    email    varchar(255),
    age      integer
);
-- роли пользователей
create table user_roles
(
    user_id bigint references users,
    role_id integer references roles,
    primary key (user_id, role_id)
);
-- правила доступа
create table permissions
(
    permission_id serial primary key,
    permission    varchar(255) not null unique
);
-- группы правил доступа
create table permission_groups
(
    group_id         serial primary key,
    permission_group varchar(255) not null unique
);
-- привязка правил в группы доступа
create table group_permissions
(
    group_id      integer references permission_groups,
    permission_id integer references permissions,
    primary key (group_id, permission_id)
);
-- привязка правил доступа к ролям (
create table role_permissions
(
    role_id       integer references roles,
    permission_id integer references permissions,
    primary key (role_id, permission_id)
);
-- привязка групп доступа к ролям
create table role_permission_groups (
    role_id integer references roles,
    group_id integer references permission_groups,
    primary key (role_id, group_id)
);

insert into roles (role)
values ('ROLE_SA'),
       ('ROLE_ADMIN'),
       ('ROLE_USER'),
       ('ROLE_CONTENT-MANAGER');
insert into permissions (permission)
values ('grant-admin-role'),
       ('edit-role'),
       ('edit-user'),
       ('edit-user-role'),
       ('read-user-info'),
       ('read-user-content'),
       ('edit-content'),
       ('edit-permission-group');
insert into permission_groups (permission_group)
values ('admin-edit-group'),
       ('user-edit-group'),
       ('content-read-group'),
       ('content-edit-group');
insert into group_permissions
values (1, 1), -- admin-edit-group: { grant-admin-role, edit-role, edit-permission-group }
       (1, 2),
       (1, 8),
       (2, 3), -- user-edit-group: { edit-user, edit-user-role, read-user-info}
       (2, 4),
       (2, 5),
       (3, 6), -- content-read-group: { read-user-content };
       (4, 7); -- content-edit-group: { edit-content };
insert into role_permission_groups
values (1, 1), -- ROLE_SA: { admin-edit-group, user-edit-group, content-read-group, content-edit-group }
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 2), -- ROLE_ADMIN: { user-edit-group }
       (3, 3), -- ROLE_USER: { content-read-group }
       (4, 3), -- ROLE_CONTENT-MANAGER: { content-read-group, content-edit-group }
       (4, 4);

-- sa:root '$2a$12$ofGl8wIqvoqlU2McOmMdjOp1ziqFdLSOYo075hxbBfPYkX0JNr5B.'
-- admin:admin '$2a$12$Izsq7LbaEg28ZPwi4pkyxuGJboPFs0Ga62Fr3hudNQPKoYA/YyASW'
-- manager:12345 '$2a$12$tQJ0WRtUdideQBL4G2iEc.aZ5T0lgNJN9hBipfnhTufuJLX/0F4uG'
-- bob:1234 '$2a$12$nJoqSubKOmtod59CyjdoU.DcaE9AH7eatWQBEkn8bPjUR5RMSDR/y'
-- jack:1234 '$2a$12$nJoqSubKOmtod59CyjdoU.DcaE9AH7eatWQBEkn8bPjUR5RMSDR/y'

insert into users (username, password, enabled, name, email, age)
values ('sa', '$2a$12$ofGl8wIqvoqlU2McOmMdjOp1ziqFdLSOYo075hxbBfPYkX0JNr5B.', true, 'super admin', 'root@example.com', null),
       ('admin', '$2a$12$Izsq7LbaEg28ZPwi4pkyxuGJboPFs0Ga62Fr3hudNQPKoYA/YyASW', true, 'просто admin', 'admin@example.com', null),
       ('bob', '$2a$12$nJoqSubKOmtod59CyjdoU.DcaE9AH7eatWQBEkn8bPjUR5RMSDR/y', true, 'Иван Иванов', 'bob@example.com', 22),
       ('jack', '$2a$12$nJoqSubKOmtod59CyjdoU.DcaE9AH7eatWQBEkn8bPjUR5RMSDR/y', true, 'Джек Джекович', 'jack@example.com', 33);

insert into user_roles
values (1,1), -- sa -> ROLE_SA
       (2,2), -- admin -> ROLE_ADMIN
       (3,3), -- bob -> ROLE_USER
       (4,4); -- jack -> ROLE_CONTENT-MANAGER



