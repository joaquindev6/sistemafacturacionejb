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
        if (id != null && id > 0) {
            Byte state = 0;
            this.em.createQuery("UPDATE User u SET u.userHistory.state = ?1 WHERE u.id = ?2")
                    .setParameter(1, state)
                    .setParameter(2, id)
                    .executeUpdate();
        }
    }

    @Override
    public User findByUsername(String username) {
        User user;
        try {
            user = this.em.createQuery("SELECT u FROM User u WHERE u.username = ?1", User.class)
                    .setParameter(1, username)
                    .getSingleResult();
        } catch (Exception ex) {
            user = null;
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user;
        try {
            user = this.em.createQuery("SELECT u FROM User u WHERE u.email = ?1", User.class)
                    .setParameter(1, email)
                    .getSingleResult();
        } catch (Exception ex) {
            user = null;
        }
        return user;
    }
}
