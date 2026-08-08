CREATE TABLE IF NOT EXISTS request_log
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      VARCHAR(255)             NOT NULL,
    request_time TIMESTAMP WITH TIME ZONE NOT NULL,
    endpoint     VARCHAR(255)             NOT NULL
);