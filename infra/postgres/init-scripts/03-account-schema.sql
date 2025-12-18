CREATE DATABASE account_service_db;
\c account_service_db;

CREATE TABLE accounts
(
    id             UUID PRIMARY KEY,
    user_id        UUID               NOT NULL,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    account_type   VARCHAR(20)        NOT NULL,
    balance        DECIMAL(15, 2)     NOT NULL DEFAULT 0.00,
    currency       VARCHAR(3)         NOT NULL DEFAULT 'USD',
    status         VARCHAR(20)        NOT NULL DEFAULT 'ACTIVE',
    created_at     TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at     TIMESTAMP,
    version        INT                NOT NULL DEFAULT 0,
    CONSTRAINT chk_account_type CHECK (account_type IN ('SAVINGS', 'CHECKING')),
    CONSTRAINT chk_account_status CHECK (status IN ('ACTIVE', 'BLOCKED', 'CLOSED')),
    CONSTRAINT chk_balance_positive CHECK (balance >= 0)
);

CREATE TABLE transactions
(
    id                     UUID PRIMARY KEY,
    account_id             UUID           NOT NULL,
    transaction_type       VARCHAR(30)    NOT NULL,
    amount                 DECIMAL(15, 2) NOT NULL,
    balance_after          DECIMAL(15, 2) NOT NULL,
    origin_service         VARCHAR(50)    NOT NULL,
    related_transaction_id UUID,
    description            VARCHAR(255),
    created_at             TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cancelled_at            TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts (id),
    CONSTRAINT chk_transaction_type CHECK (transaction_type IN (
                                                                'DEPOSIT', 'WITHDRAWAL', 'TRANSFER_OUT', 'TRANSFER_IN',
                                                                'CARD_PURCHASE'))
);

CREATE TABLE outbox_events
(
    id             UUID PRIMARY KEY,
    aggregate_type VARCHAR(50) NOT NULL,
    aggregate_id   VARCHAR(50) NOT NULL,
    event_type     VARCHAR(50) NOT NULL,
    payload        JSONB       NOT NULL,
    created_at     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status         VARCHAR(20)          DEFAULT 'PENDING'
);

CREATE INDEX idx_accounts_user ON accounts (user_id);
CREATE INDEX idx_accounts_number ON accounts (account_number);
CREATE INDEX idx_transactions_account ON transactions (account_id);
CREATE INDEX idx_transactions_created ON transactions (created_at DESC);