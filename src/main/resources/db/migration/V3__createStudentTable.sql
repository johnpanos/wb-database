CREATE TABLE student (
    id SERIAL PRIMARY KEY NOT NULL,
    powerschool_id INTEGER NOT NULL,
    grade INTEGER NOT NULL,
    wb_user_id INTEGER NOT NULL,
    backup_email TEXT NOT NULL,
    shirt_size TEXT NOT NULL,
    polo_size TEXT NOT NULL,
    in_fll BOOLEAN NOT NULL,
    in_ftc BOOLEAN NOT NULL,
    in_frc BOOLEAN NOT NULL
);