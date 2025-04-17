package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.InvoiceLine;

public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, Long> {
    List<InvoiceLine> findByInvoiceId(Long invoiceId);
}