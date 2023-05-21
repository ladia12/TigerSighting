CREATE TABLE sighting (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tiger_id INT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    image VARCHAR(255),
    FOREIGN KEY (tiger_id) REFERENCES tiger (id)
);