package com.price.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.price.service.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // You can define custom query methods here, if needed
}
