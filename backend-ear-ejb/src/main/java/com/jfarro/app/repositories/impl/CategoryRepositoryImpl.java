package com.jfarro.app.repositories.impl;

import com.jfarro.app.annotations.Repository;
import com.jfarro.app.entities.Category;
import com.jfarro.app.repositories.CategoryRepository;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@Repository
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
        Category category = findById(id);
        if (category != null) {
            this.em.createQuery("UPDATE FROM Category c SET c.userHistory.state = 0 WHERE c.id = ?1", Category.class)
                    .setParameter(1, category.getId());
        }
    }
}
