package com.jfarro.app.services.impl;

import com.jfarro.app.annotations.Service;
import com.jfarro.app.entities.DetailInvoice;
import com.jfarro.app.entities.Invoice;
import com.jfarro.app.repositories.DetailInvoiceRepository;
import com.jfarro.app.repositories.InvoiceRepository;
import com.jfarro.app.services.InvoiceService;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Inject
    private InvoiceRepository invoiceRepository;

    @Inject
    private DetailInvoiceRepository detailInvoiceRepository;

    @Override
    public List<Invoice> findAllInvoices() {
        return this.invoiceRepository.findAll();
    }

    @Override
    public Optional<Invoice> findByIdInvoice(Long id) {
        return Optional.ofNullable(this.invoiceRepository.findById(id));
    }

    @Override
    public void saveInvoice(Invoice invoice) {
        this.invoiceRepository.save(invoice);
    }

    @Override
    public void deleteInvoice(Long id) {
        this.invoiceRepository.delete(id);
    }

    @Override
    public List<DetailInvoice> findAllDetailInvoices() {
        return this.detailInvoiceRepository.findAll();
    }

    @Override
    public Optional<DetailInvoice> findByIdDetailInvoice(Long id) {
        return Optional.ofNullable(this.detailInvoiceRepository.findById(id));
    }

    @Override
    public void saveDetailInvoice(DetailInvoice detailInvoice) {
        this.detailInvoiceRepository.save(detailInvoice);
    }

    @Override
    public void deleteDetailInvoice(Long id) {
        this.detailInvoiceRepository.delete(id);
    }
}
