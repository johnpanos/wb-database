CREATE TABLE notification (
    id SERIAL PRIMARY KEY NOT NULL,
    date_sent TIMESTAMP WITHOUT TIME ZONE,
    description TEXT NOT NULL,
    seen BOOLEAN,
    title TEXT NOT NULL,
    url TEXT,
    user_id INTEGER,
    CONSTRAINT wb_user_id_notification FOREIGN KEY (user_id)
        REFERENCES wb_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
