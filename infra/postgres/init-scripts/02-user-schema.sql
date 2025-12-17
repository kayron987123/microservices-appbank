CREATE DATABASE user_service_db;
\c user_service_db;

CREATE TABLE roles
(
    id   UUID PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR
);

CREATE TABLE users
(
    id                UUID PRIMARY KEY,
    email             VARCHAR(255) UNIQUE NOT NULL,
    password          VARCHAR(255)        NOT NULL,
    first_name        VARCHAR(100)        NOT NULL,
    last_name         VARCHAR(100)        NOT NULL,
    phone             VARCHAR(20) NOT NULL ,
    document_number   VARCHAR(50) UNIQUE  NOT NULL,
    status            VARCHAR(20)         NOT NULL DEFAULT 'ACTIVE',
    is_email_verified BOOLEAN                      DEFAULT FALSE,
    created_at        TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at        TIMESTAMP,
    CONSTRAINT chk_user_status CHECK (status IN ('ACTIVE', 'BLOCKED', 'INACTIVE'))
);

CREATE TABLE user_roles
(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
)

CREATE TABLE user_documents
(
    id              UUID PRIMARY KEY,
    user_id         UUID NOT NULL,
    document_type   VARCHAR(50) NOT NULL,
    document_number VARCHAR(50) NOT NULL,
    issued_date     DATE NOT NULL ,
    expiry_date     DATE NOT NULL ,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_user_document UNIQUE (user_id, document_type),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
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

CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_status ON users (status);