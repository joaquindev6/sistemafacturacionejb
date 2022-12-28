package com.jfarro.app.services;

import com.jfarro.app.entities.Client;
import com.jfarro.app.entities.DocumentType;
import jakarta.ejb.Local;

import java.util.List;
import java.util.Optional;

@Local
public interface ClientService {
    List<Client> findAllClients();
    Optional<Client> findByIdClient(Long id);
    void saveClient(Client client);
    void deleteClient(Long id);

    List<DocumentType> findAllDocumentTypes();
    Optional<DocumentType> findByIdDocumentType(Long id);
    void saveDocumentType(DocumentType documentType);
    void deleteDocumentType(Long id);
}
