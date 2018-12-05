ALTER TABLE purchase_order
    ADD COLUMN last_updated_by TEXT,
    ADD COLUMN updated_at TIMESTAMP WITHOUT TIME ZONE;