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
        Role role = findById(id);
        if (role != null) {
            this.em.remove(role);
        }
    }
}
