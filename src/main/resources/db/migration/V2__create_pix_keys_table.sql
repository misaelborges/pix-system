CREATE TABLE pix_keys (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    key_value VARCHAR(255) UNIQUE NOT NULL,
    key_type VARCHAR(20) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);