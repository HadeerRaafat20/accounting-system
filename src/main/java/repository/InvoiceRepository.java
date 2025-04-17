package repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByProviderNameContainingIgnoreCase(String providerName);
    List<Invoice> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
}