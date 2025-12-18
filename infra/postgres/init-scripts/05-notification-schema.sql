CREATE DATABASE notification_service_db;
\c notification_service_db;

CREATE TABLE notifications
(
    id                UUID PRIMARY KEY,
    user_id           UUID        NOT NULL,
    notification_type VARCHAR(50) NOT NULL,
    channel           VARCHAR(20) NOT NULL,
    title             VARCHAR(255),
    message           TEXT        NOT NULL,
    status            VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    reference_id      UUID,
    created_at        TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sent_at           TIMESTAMP,
    CONSTRAINT chk_notification_type CHECK (notification_type IN (
                                                                  'TRANSFER_SUCCESS', 'DEPOSIT_SUCCESS',
                                                                  'CARD_TRANSACTION',
                                                                  'LOW_BALANCE', 'ACCOUNT_BLOCKED', 'OTP_CODE')),
    CONSTRAINT chk_channel CHECK (channel IN ('EMAIL', 'SMS', 'PUSH')),
    CONSTRAINT chk_notification_status CHECK (status IN ('PENDING', 'SENT', 'FAILED'))
);

CREATE INDEX idx_notifications_user ON notifications (user_id);
CREATE INDEX idx_notifications_created ON notifications (created_at DESC);