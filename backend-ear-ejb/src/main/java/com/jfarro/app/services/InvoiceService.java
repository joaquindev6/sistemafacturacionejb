package com.jfarro.app.services;

import com.jfarro.app.entities.DetailInvoice;
import com.jfarro.app.entities.Invoice;
import jakarta.ejb.Local;

import java.util.List;
import java.util.Optional;

@Local
public interface InvoiceService {
    List<Invoice> findAllInvoices();
    Optional<Invoice> findByIdInvoice(Long id);
    void saveInvoice(Invoice invoice);
    void deleteInvoice(Long id);

    List<DetailInvoice> findAllDetailInvoices();
    Optional<DetailInvoice> findByIdDetailInvoice(Long id);
    void saveDetailInvoice(DetailInvoice detailInvoice);
    void deleteDetailInvoice(Long id);
}
