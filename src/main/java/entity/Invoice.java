package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;
    private String providerName;
    private String address;
    private Double total;
    private Double paid;
    private Double remaining;
    private String deliveredBy;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceLine> invoiceLines = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getPaid() {
		return paid;
	}

	public void setPaid(Double paid) {
		this.paid = paid;
	}

	public Double getRemaining() {
		return remaining;
	}

	public void setRemaining(Double remaining) {
		this.remaining = remaining;
	}

	public String getDeliveredBy() {
		return deliveredBy;
	}

	public void setDeliveredBy(String deliveredBy) {
		this.deliveredBy = deliveredBy;
	}

	public List<InvoiceLine> getInvoiceLines() {
		return invoiceLines;
	}

	public void setInvoiceLines(List<InvoiceLine> invoiceLines) {
		this.invoiceLines = invoiceLines;
	}
}