package com.jfarro.app.repositories.impl;

import com.jfarro.app.entities.DetailInvoice;
import com.jfarro.app.repositories.DetailInvoiceRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class DetailInvoiceRepositoryImpl implements DetailInvoiceRepository {

    @Inject
    private EntityManager em;

    @Override
    public List<DetailInvoice> findAll() {
        return this.em.createQuery("SELECT di FROM DetailInvoice di", DetailInvoice.class).getResultList();
    }

    @Override
    public DetailInvoice findById(Long id) {
        return this.em.find(DetailInvoice.class, id);
    }

    @Override
    public void save(DetailInvoice detailInvoice) {
        if (detailInvoice != null) {
            if (detailInvoice.getId() != null && detailInvoice.getId() > 0) {
                this.em.merge(detailInvoice);
            } else {
                this.em.persist(detailInvoice);
            }
        }
    }

    @Override
    public void delete(Long id) {
        DetailInvoice detailInvoice = findById(id);
        if (detailInvoice != null) {
            this.em.remove(detailInvoice);
        }
    }
}
