DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS endpoints;
-- 创建 users 表
CREATE TABLE IF NOT EXISTS users
(
    userId      INTEGER PRIMARY KEY,
    accountName TEXT NOT NULL,
    role        TEXT NOT NULL
);

-- 创建 endpoints 表
CREATE TABLE IF NOT EXISTS endpoints
(
    userId   INTEGER NOT NULL,
    endpoint TEXT    NOT NULL,
    PRIMARY KEY (userId, endpoint),
    FOREIGN KEY (userId) REFERENCES users (userId)
);

-- 插入一些初始数据到 users 表
INSERT INTO users (userId, accountName, role)
VALUES (123456, 'XXXXXXX', 'admin');
INSERT INTO users (userId, accountName, role)
VALUES (789012, 'YYYYYYY', 'user');

-- 插入一些初始数据到 endpoints 表
INSERT INTO endpoints (userId, endpoint)
VALUES (123456, 'resource A');
INSERT INTO endpoints (userId, endpoint)
VALUES (123456, 'resource B');
INSERT INTO endpoints (userId, endpoint)
VALUES (123456, 'resource C');
INSERT INTO endpoints (userId, endpoint)
VALUES (789012, 'resource D');
