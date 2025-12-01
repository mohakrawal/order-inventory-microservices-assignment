DROP TABLE IF EXISTS batch;
DROP TABLE IF EXISTS product;

CREATE TABLE product (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE batch (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    expiry_date DATE,
    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
        REFERENCES product(id)
);
