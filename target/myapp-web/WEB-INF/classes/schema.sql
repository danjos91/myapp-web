CREATE TABLE IF NOT EXISTS posts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    text TEXT NOT NULL,
    short_description VARCHAR(256) NOT NULL,
    image_path VARCHAR(512),
    likes INTEGER DEFAULT 0
    );


INSERT INTO posts(title, text, short_description, image_path, likes)
VALUES ('Vacations', 'This is a post about wonderful vacations, when are u going on vacations', 'vacations of my dreams',
        'images/default.jpg', 0);

INSERT INTO posts(title, text, short_description, image_path, likes)
VALUES ('Animals', 'This is a post about animals, about pets and everything about animals', 'animals and pets',
        'images/default.jpg', 0);

INSERT INTO posts(title, text, short_description, image_path, likes)
VALUES ('Hobbies', 'This is a post about wonderful hobbies', 'my favorite hobbies',
        'images/default.jpg', 0);

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