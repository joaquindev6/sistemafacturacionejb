package com.jfarro.app.repositories.impl;

import com.jfarro.app.entities.Client;
import com.jfarro.app.repositories.ClientRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class ClientRepositoryImpl implements ClientRepository {

    @Inject
    private EntityManager em;

    @Override
    public List<Client> findAll() {
        return this.em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }

    @Override
    public Client findById(Long id) {
        return this.em.find(Client.class, id);
    }

    @Override
    public void save(Client client) {
        if (client != null) {
            if (client.getId() != null && client.getId() > 0) {
                this.em.merge(client);
            } else {
                this.em.persist(client);
            }
        }
    }

    @Override
    public void delete(Long id) {
        Client client = findById(id);
        if (client != null) {
            this.em.createQuery("UPDATE FROM Client c SET c.userHistory.state = 0 WHERE c.id = ?1", Client.class)
                    .setParameter(1, client.getId());
        }
    }
}
