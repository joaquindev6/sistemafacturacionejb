package com.jfarro.app.repositories.impl;

import com.jfarro.app.entities.Invoice;
import com.jfarro.app.repositories.InvoiceRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class InvoiceRepositoryImpl implements InvoiceRepository {

    @Inject
    private EntityManager em;

    @Override
    public List<Invoice> findAll() {
        return this.em.createQuery("SELECT i FROM Invoice i", Invoice.class).getResultList();
    }

    @Override
    public Invoice findById(Long id) {
        return this.em.find(Invoice.class, id);
    }

    @Override
    public void save(Invoice invoice) {
        if (invoice != null) {
            if (invoice.getId() != null && invoice.getId() > 0) {
                this.em.merge(invoice);
            } else {
                this.em.persist(invoice);
            }
        }
    }

    @Override
    public void delete(Long id) {
        Invoice invoice = findById(id);
        if (invoice != null) {
            this.em.remove(invoice);
        }
    }
}
