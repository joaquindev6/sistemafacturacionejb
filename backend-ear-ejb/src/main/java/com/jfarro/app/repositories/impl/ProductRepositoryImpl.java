package com.jfarro.app.repositories.impl;

import com.jfarro.app.entities.Product;
import com.jfarro.app.repositories.ProductRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class ProductRepositoryImpl implements ProductRepository {

    @Inject
    private EntityManager em;

    @Override
    public List<Product> findAll() {
        return this.em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    @Override
    public Product findById(Long id) {
        return this.em.find(Product.class, id);
    }

    @Override
    public void save(Product product) {
        if (product != null) {
            if (product.getId() != null && product.getId() > 0) {
                this.em.merge(product);
            } else {
                this.em.persist(product);
            }
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null && id > 0) {
            Byte state = 0;
            this.em.createQuery("UPDATE Product p SET p.userHistory.state = ?1 WHERE p.id = ?2")
                    .setParameter(1, state)
                    .setParameter(2, id)
                    .executeUpdate();
        }
    }
}
