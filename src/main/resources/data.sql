DROP TABLE IF EXISTS role_definition;

CREATE TABLE role_definition (
	id bigint NOT NULL,
    role_name character varying(255) COLLATE pg_catalog."default",
    role_desc character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT role_definition_pkey PRIMARY KEY (id)
);

INSERT INTO role_definition (id, role_name, role_desc)
VALUES (1, 'ROLE_SUPER_ADMIN', 'An user who has full access of the system.');

INSERT INTO role_definition (id, role_name, role_desc)
VALUES (2, 'ROLE_ADMIN', 'An user directly under super admin with most of super admin privilege.');

INSERT INTO role_definition (id, role_name, role_desc)
VALUES (3, 'ROLE_INSTRUCTOR', 'An user who can public, edit, delete, update a course.');

INSERT INTO role_definition (id, role_name, role_desc)
VALUES (4, 'ROLE_USER', 'This is the default role every user get upon signing up.');