CREATE DATABASE card_service_db;
\c card_service_db;

CREATE TABLE card_types
(
    id            UUID PRIMARY KEY,
    name          VARCHAR(50) UNIQUE NOT NULL,
    card_network  VARCHAR(20)        NOT NULL,
    card_category VARCHAR(20)        NOT NULL,
    annual_fee    DECIMAL(10, 2)              DEFAULT 0.00,
    description   VARCHAR(255),
    is_active     BOOLEAN                     DEFAULT TRUE,
    created_at    TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_card_network CHECK (card_network IN ('VISA', 'MASTERCARD')),
    CONSTRAINT chk_card_category CHECK (card_category IN ('DEBIT', 'CREDIT'))
);

CREATE TABLE cards
(
    id               UUID PRIMARY KEY,
    account_id       UUID               NOT NULL,
    user_id          UUID               NOT NULL,
    card_type_id     UUID               NOT NULL,
    card_number      VARCHAR(19) UNIQUE NOT NULL,
    card_holder_name VARCHAR(100)       NOT NULL,
    expiry_month     INT                NOT NULL,
    expiry_year      INT                NOT NULL,
    cvv              VARCHAR(255)       NOT NULL,
    status           VARCHAR(20)        NOT NULL DEFAULT 'ACTIVE',
    daily_limit      DECIMAL(15, 2)     NOT NULL DEFAULT 5000.00,
    created_at       TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cancelled_at     TIMESTAMP,
    FOREIGN KEY (card_type_id) REFERENCES card_types (id),
    CONSTRAINT chk_card_status CHECK (status IN ('ACTIVE', 'BLOCKED', 'EXPIRED', 'CANCELLED')),
    CONSTRAINT chk_expiry_month CHECK (expiry_month BETWEEN 1 AND 12)
);

CREATE TABLE card_transactions
(
    id               UUID PRIMARY KEY,
    card_id          UUID           NOT NULL,
    account_id       UUID           NOT NULL,
    amount           DECIMAL(15, 2) NOT NULL,
    merchant_name    VARCHAR(255),
    transaction_type VARCHAR(30)    NOT NULL,
    status           VARCHAR(20)    NOT NULL DEFAULT 'COMPLETED',
    created_at       TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cancelled_at      TIMESTAMP,
    FOREIGN KEY (card_id) REFERENCES cards (id),
    CONSTRAINT chk_card_tx_type CHECK (transaction_type IN ('PURCHASE', 'WITHDRAWAL', 'REFUND')),
    CONSTRAINT chk_card_tx_status CHECK (status IN ('COMPLETED', 'DECLINED', 'REVERSED'))
);

CREATE TABLE outbox_events
(
    id             UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
    aggregate_type VARCHAR(50) NOT NULL,
    aggregate_id   VARCHAR(50) NOT NULL,
    event_type     VARCHAR(50) NOT NULL,
    payload        JSONB       NOT NULL,
    created_at     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status         VARCHAR(20)          DEFAULT 'PENDING'
);

CREATE INDEX idx_cards_account ON cards (account_id);
CREATE INDEX idx_card_transactions_created ON card_transactions (created_at DESC);

INSERT INTO card_types (id, name, card_network, card_category, annual_fee, description, is_active, created_at)
VALUES
    (gen_random_uuid(), 'VISA_DEBIT_CLASSIC', 'VISA', 'DEBIT', 0.00, 'Tarjeta de débito estándar', true, NOW()),
    (gen_random_uuid(), 'MASTERCARD_GOLD', 'MASTERCARD', 'CREDIT', 50.00, 'Tarjeta de crédito Gold', true, NOW());