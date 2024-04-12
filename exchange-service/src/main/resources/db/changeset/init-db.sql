--liquibase formatted sql

--changeset admin:1
CREATE TABLE IF NOT EXISTS history_rate (
    id bigint GENERATED ALWAYS AS IDENTITY,
    actual_date DATE,
    currency VARCHAR(3),
    usd_rate DOUBLE PRECISION,

    unique(actual_date, currency)
);
--rollback DROP TABLE rate_history_value;