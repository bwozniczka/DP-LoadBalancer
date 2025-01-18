CREATE TABLE Users (
    id NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username VARCHAR2(50) NOT NULL UNIQUE,
    password VARCHAR2(50) NOT NULL,
    email VARCHAR2(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample users
INSERT INTO Users (username, password, email) VALUES ('user1', 'pass1', 'user1@example.com');
INSERT INTO Users (username, password, email) VALUES ('user2', 'pass2', 'user2@example.com');
INSERT INTO Users (username, password, email) VALUES ('user3', 'pass3', 'user3@example.com');