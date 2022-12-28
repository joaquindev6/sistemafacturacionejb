package com.jfarro.app.repositories.impl;

import com.jfarro.app.entities.User;
import com.jfarro.app.repositories.UserRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class UserRepositoryImpl implements UserRepository {

    @Inject
    private EntityManager em;

    @Override
    public List<User> findAll() {
        return this.em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User findById(Long id) {
        return this.em.find(User.class, id);
    }

    @Override
    public void save(User user) {
        if (user != null) {
            if (user.getId() != null && user.getId() > 0) {
                this.em.merge(user);
            } else {
                this.em.persist(user);
            }
        }
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        if (user != null) {
            this.em.createQuery("UPDATE FROM User u SET u.userHistory.state = 0 WHERE u.id = ?1", User.class)
                    .setParameter(1, user.getId());
        }
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }
}
