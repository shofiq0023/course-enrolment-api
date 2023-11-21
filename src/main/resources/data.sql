DROP TABLE IF EXISTS role_definition;

CREATE TABLE role_definition (
	id bigint NOT NULL,
    role_name character varying(255) COLLATE pg_catalog."default",
    role_desc character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT role_definition_pkey PRIMARY KEY (id)
);

INSERT INTO role_definition ("id", "role_name", "role_desc")
VALUES (1, 'ROLE_SUPER_ADMIN', 'An user who has full access of the system.');

INSERT INTO role_definition ("id", "role_name", "role_desc")
VALUES (2, 'ROLE_ADMIN', 'An user directly under super admin with most of super admin privilege.');

INSERT INTO role_definition ("id", "role_name", "role_desc")
VALUES (3, 'ROLE_INSTRUCTOR', 'An user who can public, edit, delete, update a course.');

INSERT INTO role_definition ("id", "role_name", "role_desc")
VALUES (4, 'ROLE_USER', 'This is the default role every user get upon signing up.');

-- Insert user data
INSERT INTO users ("username", "name", "email", "mobile", "password", "address", "date_of_birth", "roles", "signup_date")
VALUES ('super.admin', 'Super admin user', 'super@gmail.com', '01478520124', '$2a$10$5/3XMOb8gxtexZyjmZQOJeKTyGgJgbujO1uwP6O/7tqoJppYno7QS', 'Dhaka, Bangladesh', '1995-02-25', 'ROLE_USER, ROLE_SUPER_ADMIN', now());

INSERT INTO users ("username", "name", "email", "mobile", "password", "address", "date_of_birth", "roles", "signup_date")
VALUES ('admin', 'Admin user', 'admin@gmail.com', '01478520121', '$2a$10$5/3XMOb8gxtexZyjmZQOJeKTyGgJgbujO1uwP6O/7tqoJppYno7QS', 'Dhaka, Bangladesh', '1995-02-01', 'ROLE_USER, ROLE_ADMIN', now());

INSERT INTO users ("username", "name", "email", "mobile", "password", "address", "date_of_birth", "roles", "signup_date")
VALUES ('instructor', 'Instructor user', 'instructor@gmail.com', '01478520122', '$2a$10$5/3XMOb8gxtexZyjmZQOJeKTyGgJgbujO1uwP6O/7tqoJppYno7QS', 'Dhaka, Bangladesh', '1995-02-02', 'ROLE_USER, ROLE_INSTRUCTOR', now());

INSERT INTO users ("username", "name", "email", "mobile", "password", "address", "date_of_birth", "roles", "signup_date")
VALUES ('instructor2', 'Instructor user 2', 'instructor2@gmail.com', '01478520126', '$2a$10$5/3XMOb8gxtexZyjmZQOJeKTyGgJgbujO1uwP6O/7tqoJppYno7QS', 'Dhaka, Bangladesh', '1995-03-20', 'ROLE_USER, ROLE_INSTRUCTOR', now());

INSERT INTO users ("username", "name", "email", "mobile", "password", "address", "date_of_birth", "roles", "signup_date")
VALUES ('user', 'Normal user', 'user@gmail.com', '01478520123', '$2a$10$5/3XMOb8gxtexZyjmZQOJeKTyGgJgbujO1uwP6O/7tqoJppYno7QS', 'Dhaka, Bangladesh', '1995-02-28', 'ROLE_USER', now());

INSERT INTO users ("username", "name", "email", "mobile", "password", "address", "date_of_birth", "roles", "signup_date")
VALUES ('shofiqul', 'Shofiqul Islam', 'shofiqul@gmail.com', '01618638686', '$2a$10$5/3XMOb8gxtexZyjmZQOJeKTyGgJgbujO1uwP6O/7tqoJppYno7QS', 'Dhaka, Bangladesh', '1995-02-28', 'ROLE_USER', now());

-- Insert course data
INSERT INTO courses ("title", "description", "topic", "created_time", "active", "instructor_id")
VALUES ('Java for beginners', 'This course is for the people who are new to Java', 'java, oop, basic java', now(), true, 3);

INSERT INTO courses ("title", "description", "topic", "created_time", "active", "instructor_id")
VALUES ('Spring boot basic', 'This course is for the people who are new to Spring boot', 'java, spring, spring-boot', now(), true, 3);

INSERT INTO courses ("title", "description", "topic", "created_time", "active", "instructor_id")
VALUES ('C# and .NET for beginners', 'This course is for the people who are new to C# and .NET framework', 'c#, .net, dotnet', now(), true, 3);

INSERT INTO courses ("title", "description", "topic", "created_time", "active", "instructor_id")
VALUES ('Fullstack Web development', 'Fullstack web development course', 'html, css, js, javascript, fullstack', now(), true, 4);

INSERT INTO courses ("title", "description", "topic", "created_time", "active", "instructor_id")
VALUES ('Modern JavaScript', 'Modern javascript course with ES6', 'js, javascript, es6', now(), true, 4);
