CREATE TABLE IF NOT EXISTS posts (
        id BIGSERIAL PRIMARY KEY,
        title VARCHAR(256) NOT NULL,
        text TEXT NOT NULL,
        short_description VARCHAR(256) NOT NULL,
        image_path VARCHAR(512),
        likes INTEGER DEFAULT 0
    );

INSERT INTO posts(title, text, short_description, image_path, likes) values ("Vacations", "This is a post about wonderful vacations, when are u going on vacations", "vacations of my dreams",
                  "images/default.jpg", 0);
INSERT INTO posts(title, text, short_description, image_path, likes) values ("Animals", "This is a post about animals, about pets and everythin about animals", "vacations of my dreams",
                  "images/default.jpg", 0);
INSERT INTO posts(title, text, short_description, image_path, likes) values ("Hobbies", "This is a post about wonderful hobbies", "vacations of my dreams",
                  "images/default.jpg", 0);
-- insert into users(first_name, last_name, age, active) values ('Иван', 'Иванов', 30, true);
-- insert into users(first_name, last_name, age, active) values ('Пётр', 'Петров', 25, false);
-- insert into users(first_name, last_name, age, active) values ('Мария', 'Сидорова', 28, true);