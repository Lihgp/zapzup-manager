INSERT INTO zapzup.token_entity (id,
                                 code,
                                 user_id,
                                 expiration_date)
VALUES ('TOKEN-ID',
        '89SHAF89-FHD8SAYF',
        'USER-ID',
        current_timestamp + interval '1 day');

CREATE TABLE IF NOT EXISTS zapzup.chat_entity
(
    id                   VARCHAR(41) PRIMARY KEY NOT NULL,
    name                 VARCHAR(40),
    description          VARCHAR(255),
    status               varchar(10),
    file_id              VARCHAR(40),
    created_by           VARCHAR(60),
    updated_by           VARCHAR(60),
    deleted_by           VARCHAR(60),
    created_at           TIMESTAMP,
    updated_at           TIMESTAMP,
    deleted_at           TIMESTAMP,
    last_message_sent_at TIMESTAMP,

    CONSTRAINT fk_file_id FOREIGN KEY (file_id) REFERENCES zapzup.file_entity (id)
);