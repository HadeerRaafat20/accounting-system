package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Invoice;
import entity.InvoiceLine;
import repository.InvoiceLineRepository;
import repository.InvoiceRepository;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepo;
    @Autowired
    private InvoiceLineRepository invoiceLineRepo;

    public Invoice addInvoice(Invoice invoice) {
        invoice.setTotal(calculateTotal(invoice));
        invoice.setRemaining(invoice.getTotal() - invoice.getPaid());
        invoice.getInvoiceLines().forEach(line -> {
            line.setValue(line.getQuantity() * line.getPrice());
            line.setInvoice(invoice);
        });
        
        return invoiceRepo.save(invoice);
    }

    public Invoice updateInvoice(Long id, Invoice updated) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow();
        invoice.setDateTime(updated.getDateTime());
        invoice.setProviderName(updated.getProviderName());
        invoice.setAddress(updated.getAddress());
        invoice.setDeliveredBy(updated.getDeliveredBy());
        invoice.setPaid(updated.getPaid());
        invoice.setTotal(calculateTotal(invoice));
        invoice.setRemaining(invoice.getTotal() - invoice.getPaid());
        return invoiceRepo.save(invoice);
    }

    public void deleteInvoice(Long id) {
        invoiceRepo.deleteById(id);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepo.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepo.findById(id).orElseThrow();
    }

    private double calculateTotal(Invoice invoice) {
        return invoice.getInvoiceLines().stream().mapToDouble(InvoiceLine::getValue).sum();
    }
}