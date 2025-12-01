INSERT INTO product (id, name)
VALUES
 (1, 'Laptop'),
 (2, 'Mobile'),
 (3, 'Headphones');

-- Batches for Product 1 (Laptop)
INSERT INTO batch (product_id, quantity, expiry_date) VALUES
 (1, 30, '2025-12-31'),
 (1, 20, '2025-10-10'),
 (1, 50, '2026-01-20');

-- Batches for Product 2
INSERT INTO batch (product_id, quantity, expiry_date) VALUES
 (2, 40, '2025-11-11'),
 (2, 10, '2026-05-01');

-- Batches for Product 3
INSERT INTO batch (product_id, quantity, expiry_date) VALUES
 (3, 15, '2025-09-09');
