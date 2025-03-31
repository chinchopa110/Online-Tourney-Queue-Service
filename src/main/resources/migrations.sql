CREATE TABLE card (
                      id VARCHAR(255) PRIMARY KEY,
                      person_id VARCHAR(255) NOT NULL,
                      elo INT NOT NULL,
                      federation VARCHAR(255) NOT NULL,
                      created_at TIMESTAMP NOT NULL
);