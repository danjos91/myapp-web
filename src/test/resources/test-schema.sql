
DROP TABLE IF EXISTS posts;
CREATE TABLE IF NOT EXISTS posts (
                                     id BIGSERIAL PRIMARY KEY,
                                     title VARCHAR(256) NOT NULL,
                                     text TEXT NOT NULL,
                                     image_path VARCHAR(512),
                                     tags TEXT,
                                     likes INTEGER DEFAULT 0
    );


DROP TABLE IF EXISTS comments;
CREATE TABLE IF NOT EXISTS comments (
                                        id BIGSERIAL PRIMARY KEY,
                                        post_id BIGINT,
                                        text TEXT NOT NULL,
                                        FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
    );