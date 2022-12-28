package com.jfarro.app.repositories.impl;

import com.jfarro.app.annotations.Repository;
import com.jfarro.app.entities.DocumentType;
import com.jfarro.app.repositories.DocumentTypeRepository;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@Repository
public class DocumentTypeRepositoryImpl implements DocumentTypeRepository {

    @Inject
    private EntityManager em;

    @Override
    public List<DocumentType> findAll() {
        return this.em.createQuery("SELECT d FROM DocumentType d", DocumentType.class).getResultList();
    }

    @Override
    public DocumentType findById(Long id) {
        return this.em.find(DocumentType.class, id);
    }

    @Override
    public void save(DocumentType documentType) {
        if (documentType != null) {
            if (documentType.getId() != null && documentType.getId() > 0) {
                this.em.merge(documentType);
            } else {
                this.em.persist(documentType);
            }
        }
    }

    @Override
    public void delete(Long id) {
        DocumentType documentType = findById(id);
        if (documentType != null) {
            this.em.remove(documentType);
        }
    }
}
