CREATE TABLE requests (
 id INTEGER PRIMARY KEY AUTOINCREMENT,
 timestamp BIGINTEGER NOT NULL,
 result INTEGER NOT NULL,
 message VARCHAR(200) NOT NULL);
CREATE INDEX idx_requests_result ON requests (result);