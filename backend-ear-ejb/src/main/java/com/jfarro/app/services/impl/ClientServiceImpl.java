package com.jfarro.app.services.impl;

import com.jfarro.app.entities.Client;
import com.jfarro.app.entities.DocumentType;
import com.jfarro.app.repositories.ClientRepository;
import com.jfarro.app.repositories.DocumentTypeRepository;
import com.jfarro.app.services.ClientService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@Stateless //Debe ser este tipo de anotacion porque si creamos una anotacion no lo reconoce como una anotacion del beans
public class ClientServiceImpl implements ClientService {

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public List<Client> findAllClients() {
        return this.clientRepository.findAll();
    }

    @Override
    public Optional<Client> findByIdClient(Long id) {
        return Optional.ofNullable(this.clientRepository.findById(id));
    }

    @Override
    public Optional<Client> findByEmailClient(String email) {
        return Optional.ofNullable(this.clientRepository.findByEmail(email));
    }

    @Override
    public Optional<Client> findByNroDocClient(String nroDocu) {
        return Optional.ofNullable(this.clientRepository.findByNroDoc(nroDocu));
    }

    @Override
    public void saveClient(Client client) {
        this.clientRepository.save(client);
    }

    @Override
    public void deleteClient(Long id) {
        this.clientRepository.delete(id);
    }

    @Override
    public List<DocumentType> findAllDocumentTypes() {
        return this.documentTypeRepository.findAll();
    }

    @Override
    public Optional<DocumentType> findByIdDocumentType(Long id) {
        return Optional.ofNullable(this.documentTypeRepository.findById(id));
    }

    @Override
    public void saveDocumentType(DocumentType documentType) {
        this.documentTypeRepository.save(documentType);
    }

    @Override
    public void deleteDocumentType(Long id) {
        this.documentTypeRepository.delete(id);
    }
}
