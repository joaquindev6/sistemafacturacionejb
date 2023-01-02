package com.jfarro.app.repositories.impl;

import com.jfarro.app.entities.Role;
import com.jfarro.app.repositories.RoleRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class RoleRepositoryImpl implements RoleRepository {

    @Inject
    private EntityManager em;

    @Override
    public List<Role> findAll() {
        return this.em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public Role findById(Long id) {
        return this.em.find(Role.class, id);
    }

    @Override
    public void save(Role role) {
        if (role != null) {
            if (role.getId() != null && role.getId() > 0) {
                this.em.merge(role);
            } else {
                this.em.persist(role);
            }
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null && id > 0) {
            Byte state = 0;
            this.em.createQuery("UPDATE Role r SET r.userHistory.state = ?1 WHERE r.id = ?2")
                    .setParameter(1, state)
                    .setParameter(2, id)
                    .executeUpdate();
        }
    }

    @Override
    public Role findByName(String name) {
        Role role;
        try {
            role = this.em.createQuery("SELECT r FROM Role r WHERE r.name = ?1", Role.class)
                    .setParameter(1, name)
                    .getSingleResult();
        } catch (Exception ex) {
            role = null;
        }
        return role;
    }
}
