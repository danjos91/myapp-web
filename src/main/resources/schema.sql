CREATE TABLE IF NOT EXISTS posts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    text TEXT NOT NULL,
    short_description VARCHAR(256) NOT NULL,
    image_path VARCHAR(512),
    likes INTEGER DEFAULT 0
    );

INSERT INTO posts(title, text, short_description, image_path, likes)
VALUES ('Vacations',
        'This is a post about wonderful vacations, when are u going on vacations',
        'vacations of my dreams',
        'C:\Users\Женя\Pictures\example.jpg',
        0);

INSERT INTO posts(title, text, short_description, image_path, likes)
VALUES ('Animals',
        'This is a post about animals, about pets and everything about animals',
        'animals and pets',
        'C:\Users\Женя\Pictures\example.jpg',
        0);

INSERT INTO posts(title, text, short_description, image_path, likes)
VALUES ('Hobbies',
        'This is a post about wonderful hobbies',
        'my favorite hobbies',
        'C:\Users\Женя\Pictures\example.jpg',
        0);

CREATE TABLE IF NOT EXISTS comments (
                                        id BIGSERIAL PRIMARY KEY,
                                        post_id BIGINT,
                                        text TEXT NOT NULL,
                                        FOREIGN KEY (post_id) REFERENCES posts(id)
);

INSERT INTO comments(post_id, text) VALUES (1,'This is an excelent post');
INSERT INTO comments(post_id, text) VALUES (1,'Wonderful post');
INSERT INTO comments(post_id, text) VALUES (3,'Wonderful post');
INSERT INTO comments(post_id, text) VALUES (3,'Very interesting post');
INSERT INTO comments(post_id, text) VALUES (3,'Wow what a post');
INSERT INTO comments(post_id, text) VALUES (3,'Magnific');
INSERT INTO comments(post_id, text) VALUES (3,':)');


CREATE TABLE IF NOT EXISTS tags (
                                    id BIGSERIAL PRIMARY KEY,
                                    post_id BIGINT,
                                    text TEXT NOT NULL,
                                    FOREIGN KEY (post_id) REFERENCES posts(id)
);
INSERT INTO comments(post_id, text) VALUES (1,'vacation');
INSERT INTO comments(post_id, text) VALUES (1,'interesting');
INSERT INTO comments(post_id, text) VALUES (1,'special');
INSERT INTO comments(post_id, text) VALUES (1,'fun');
INSERT INTO comments(post_id, text) VALUES (2,'interesting');
INSERT INTO comments(post_id, text) VALUES (3,'interesting');
INSERT INTO comments(post_id, text) VALUES (3,'special');
INSERT INTO comments(post_id, text) VALUES (3,'fun');

-- Таблица с пользователями
create table if not exists users(
                                    id bigserial primary key,
                                    first_name varchar(256) not null,
    last_name varchar(256) not null,
    age integer not null,
    active boolean not null);

insert into users(first_name, last_name, age, active) values ('Иван', 'Иванов', 30, true);
insert into users(first_name, last_name, age, active) values ('Пётр', 'Петров', 25, false);
insert into users(first_name, last_name, age, active) values ('Мария', 'Сидорова', 28, true);