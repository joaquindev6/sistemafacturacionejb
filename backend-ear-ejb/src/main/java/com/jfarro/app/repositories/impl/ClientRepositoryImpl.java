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
        if (id != null && id > 0) {
            Byte state = 0;
            this.em.createQuery("UPDATE Client c SET c.userHistory.state = ?1 WHERE c.id = ?2")
                    .setParameter(1, state)
                    .setParameter(2, id)
                    .executeUpdate();
        }
    }

    @Override
    public Client findByEmail(String email) {
        Client client;
        try {
            client = this.em.createQuery("SELECT c FROM Client c WHERE c.email = ?1", Client.class)
                    .setParameter(1, email)
                    .getSingleResult();
        } catch (Exception ex) {
            client = null;
        }
        return client;
    }

    @Override
    public Client findByNroDoc(String nroDocu) {
        Client client;
        try {
            client = this.em.createQuery("SELECT c FROM Client c WHERE c.nroDoc = ?1", Client.class)
                    .setParameter(1, nroDocu)
                    .getSingleResult();
        } catch (Exception ex) {
            client = null;
        }
        return client;
    }
}
