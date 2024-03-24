ALTER TABLE products
    ADD COLUMN created_by VARCHAR(51),
    ADD COLUMN created_at DATETIME,
    ADD COLUMN updated_by VARCHAR(51),
    ADD COLUMN updated_at DATETIME;

