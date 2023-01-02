package com.jfarro.app.repositories.impl;

import com.jfarro.app.entities.Category;
import com.jfarro.app.repositories.CategoryRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class CategoryRepositoryImpl implements CategoryRepository {

    @Inject
    private EntityManager em;

    @Override
    public List<Category> findAll() {
        return this.em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }

    @Override
    public Category findById(Long id) {
        return this.em.find(Category.class, id);
    }

    @Override
    public void save(Category category) {
        if (category != null) {
            if (category.getId() != null && category.getId() > 0) {
                this.em.merge(category);
            } else {
                this.em.persist(category);
            }
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null && id > 0) {
            Byte state = 0;
            this.em.createQuery("UPDATE Category c SET c.userHistory.state = ?1 WHERE c.id = ?2")
                    .setParameter(1, state)
                    .setParameter(2, id)
                    .executeUpdate();
        }
    }

    @Override
    public Category findByName(String name) {
        Category category;
        try {
            category = this.em.createQuery("SELECT c FROM Category c WHERE c.name = ?1", Category.class)
                    .setParameter(1, name)
                    .getSingleResult();
        } catch (Exception ex) {
            category = null;
        }
        return category;
    }
}
