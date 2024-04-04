--liquibase formatted sql

--changeset admin:1
CREATE TABLE IF NOT EXISTS rate_history_date (id bigserial PRIMARY KEY, actual_date DATE);
--rollback DROP TABLE rate_history_date;

--changeset admin:2
CREATE TABLE IF NOT EXISTS rate_history_value (
    id bigserial PRIMARY KEY,
    history_date_id bigserial,
    currency VARCHAR(3),
    rate DOUBLE PRECISION,
    FOREIGN KEY (history_date_id) REFERENCES rate_history_date(id)
);
--rollback DROP TABLE rate_history_value;

--changeset admin:3
CREATE UNIQUE index if NOT EXISTS idx_rhv_date_currency ON rate_history_value (history_date_id, currency);
--rollback DROP INDEX idx_rhv_date_currency;