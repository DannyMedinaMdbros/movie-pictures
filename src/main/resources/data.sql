
CREATE TABLE movie_picture (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    image_url VARCHAR(255),
    favorites_counter INT,
    released_year INT
);

INSERT INTO movie_picture (image_url, favorites_counter, released_year) VALUES
('https://s3bucket.com/picture1.jpg', 10, 2019),
('https://s3bucket.com/picture2.jpg', 5, 1994),
('https://s3bucket.com/picture3.jpg', 20, 2004);