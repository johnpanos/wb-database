CREATE TABLE purchase_order (
    id SERIAL PRIMARY KEY NOT NULL,
    user_id INTEGER NOT NULL REFERENCES wb_user(id) MATCH SIMPLE,
    date_ordered DATE NOT NULL,
    need_by_date DATE NOT NULL,
    part_name TEXT NOT NULL,
    part_number TEXT NOT NULL,
    quantity INTEGER NOT NULL,
    cost_per_item DECIMAL NOT NULL,
    part_id INTEGER REFERENCES part(id) MATCH SIMPLE,
    asap BOOLEAN NOT NULL
);