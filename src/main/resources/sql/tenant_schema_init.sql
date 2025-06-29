CREATE TABLE IF NOT EXISTS ${schema}.users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS ${schema}.accounts (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS ${schema}.journal_entries (
    id BIGSERIAL PRIMARY KEY,
    description TEXT,
    entry_date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS ${schema}.journal_lines (
    id BIGSERIAL PRIMARY KEY,
    journal_entry_id BIGINT REFERENCES journal_entries(id),
    account_id BIGINT REFERENCES accounts(id),
    debit NUMERIC(18,2) DEFAULT 0,
    credit NUMERIC(18,2) DEFAULT 0
);