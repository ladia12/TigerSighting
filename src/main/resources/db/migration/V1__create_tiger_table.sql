CREATE TABLE tiger (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    last_seen_timestamp TIMESTAMP,
    last_seen_latitude DOUBLE NOT NULL,
    last_seen_longitude DOUBLE NOT NULL
);