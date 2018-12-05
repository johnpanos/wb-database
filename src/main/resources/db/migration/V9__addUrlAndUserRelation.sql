ALTER TABLE purchase_order
    ADD COLUMN url TEXT;

CREATE TABLE wb_user_purchase_orders (
    wb_user_id INTEGER NOT NULL,
    purchase_orders_id INTEGER NOT NULL
);
