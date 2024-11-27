-- V1__create_invoice_table.sql
CREATE TABLE invoice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_amount INT NOT NULL,
    invoice_id VARCHAR(255) NOT NULL
);
