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