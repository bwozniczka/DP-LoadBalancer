CREATE TABLE IF NOT EXISTS Users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert 3 users into the Users table
INSERT INTO Users (username, password, email) VALUES ('user1', 'pass1', 'user1@example.com');
INSERT INTO Users (username, password, email) VALUES ('user2', 'pass2', 'user2@example.com');
INSERT INTO Users (username, password, email) VALUES ('user3', 'pass3', 'user3@example.com');