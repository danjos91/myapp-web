CREATE TABLE IF NOT EXISTS posts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    text TEXT NOT NULL,
    image_path VARCHAR(512),
    tags TEXT,
    likes INTEGER DEFAULT 0
    );

INSERT INTO posts(title, text, image_path, tags, likes)
VALUES ('Vacations1',
        'This is a post about wonderful vacations, when are u going on vacations',
        'none1',
        'vacation, fun, rest, отдых, красиво',
        0);

INSERT INTO posts(title, text, image_path, tags, likes)
VALUES ('Animals2',
        'This is a post about animals, about pets and everything about animals',
        'none2',
        'work, работа',
        0);

INSERT INTO posts(title, text, image_path, tags, likes)
VALUES ('Hobbies3',
        'This is a post about wonderful hobbies',
        'none3',
        'hobby, fun, rest, развлечение',
        0);

INSERT INTO posts(title, text, image_path, tags, likes)
VALUES ('Vacations4',
        'This is a post about wonderful vacations, when are u going on vacations',
        'none1',
        'vacation, fun, rest, отдых, красиво',
        0);

INSERT INTO posts(title, text, image_path, tags, likes)
VALUES ('Animals5',
        'This is a post about animals, about pets and everything about animals',
        'none2',
        'work, работа',
        0);

INSERT INTO posts(title, text, image_path, tags, likes)
VALUES ('Hobbies6',
        'This is a post about wonderful hobbies',
        'none3',
        'hobby, fun, rest, развлечение',
        0);
INSERT INTO posts(title, text, image_path, tags, likes)
VALUES ('Vacations7',
        'This is a post about wonderful vacations, when are u going on vacations',
        'none1',
        'vacation, fun, rest, отдых, красиво',
        0);

INSERT INTO posts(title, text, image_path, tags, likes)
VALUES ('Animals8',
        'This is a post about animals, about pets and everything about animals',
        'none2',
        'work, работа',
        0);

INSERT INTO posts(title, text, image_path, tags, likes)
VALUES ('Hobbies9',
        'This is a post about wonderful hobbies',
        'none3',
        'hobby, fun, rest, развлечение',
        0);
INSERT INTO posts(title, text, image_path, tags, likes)
VALUES ('Vacations10',
        'This is a post about wonderful vacations, when are u going on vacations',
        'none1',
        'vacation, fun, rest, отдых, красиво',
        0);

INSERT INTO posts(title, text, image_path, tags, likes)
VALUES ('Animals11',
        'This is a post about animals, about pets and everything about animals',
        'none2',
        'work, работа',
        0);

INSERT INTO posts(title, text, image_path, tags, likes)
VALUES ('Hobbies12',
        'This is a post about wonderful hobbies',
        'none3',
        'hobby, fun, rest, развлечение',
        0);

CREATE TABLE IF NOT EXISTS comments (
                                        id BIGSERIAL PRIMARY KEY,
                                        post_id BIGINT,
                                        text TEXT NOT NULL,
                                        FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

INSERT INTO comments(post_id, text) VALUES (1,'This is an excelent post');
INSERT INTO comments(post_id, text) VALUES (1,'Wonderful post');
INSERT INTO comments(post_id, text) VALUES (3,'Wonderful post');
INSERT INTO comments(post_id, text) VALUES (3,'Very interesting post');
INSERT INTO comments(post_id, text) VALUES (3,'Wow what a post');
INSERT INTO comments(post_id, text) VALUES (3,'Magnific');
INSERT INTO comments(post_id, text) VALUES (3,':)');
