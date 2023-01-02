package com.jfarro.app.repositories;

import com.jfarro.app.entities.Client;

public interface ClientRepository extends CrudRepository<Client> {
    Client findByEmail(String email);
    Client findByNroDoc(String nroDocu);
}
