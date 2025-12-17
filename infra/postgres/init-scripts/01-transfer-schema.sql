CREATE DATABASE transfer_service_db;
\c transfer_service_db;

CREATE TABLE transfers
(
    id              UUID PRIMARY KEY,
    idempotency_key VARCHAR(255) UNIQUE,
    from_account_id UUID           NOT NULL,
    to_account_id   UUID           NOT NULL,
    amount          DECIMAL(15, 2) NOT NULL,
    currency        VARCHAR(3)     NOT NULL DEFAULT 'USD',
    description     VARCHAR(255),
    status          VARCHAR(20)    NOT NULL DEFAULT 'PENDING',
    created_at      TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at    TIMESTAMP,
    cancelled_at     TIMESTAMP,
    CONSTRAINT chk_transfer_status CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED', 'REVERSED')),
    CONSTRAINT chk_amount_positive CHECK (amount > 0),
    CONSTRAINT chk_different_accounts CHECK (from_account_id != to_account_id)
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

CREATE INDEX idx_transfers_from_account ON transfers (from_account_id);
CREATE INDEX idx_transfers_to_account ON transfers (to_account_id);
CREATE INDEX idx_transfers_status ON transfers (status);


