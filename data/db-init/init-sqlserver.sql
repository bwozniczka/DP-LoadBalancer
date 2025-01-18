CREATE TABLE Users (
    id INT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(50) NOT NULL UNIQUE,
    password NVARCHAR(50) NOT NULL,
    email NVARCHAR(100) NOT NULL UNIQUE,
    created_at DATETIME DEFAULT GETDATE()
);

-- Insert 3 users into the Users table
INSERT INTO Users (username, password, email) VALUES ('user1', 'pass1', 'user1@example.com');
INSERT INTO Users (username, password, email) VALUES ('user2', 'pass2', 'user2@example.com');
INSERT INTO Users (username, password, email) VALUES ('user3', 'pass3', 'user3@example.com');