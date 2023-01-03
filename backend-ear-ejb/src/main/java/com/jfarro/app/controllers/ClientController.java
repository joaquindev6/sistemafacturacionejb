package com.jfarro.app.controllers;

import com.jfarro.app.entities.Client;
import com.jfarro.app.entities.User;
import com.jfarro.app.services.ClientService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestScoped
public class ClientController {

    @Inject
    private ClientService clientService;

    public Map<String, String> dataValidationController(Client client, User userLogin) {
        //Validando los campos del formulario
        Map<String, String> messages = new HashMap<>();
        if (client.getNames() == null || client.getNames().isBlank()) {
            messages.put("namesError", "El campo nombres no puede estar vacío.");
        }
        if (client.getApePat() == null || client.getApePat().isBlank()) {
            messages.put("apePatError", "El campo apellido paterno no puede estar vacío.");
        }
        if (client.getApeMat() == null || client.getApeMat().isBlank()) {
            messages.put("apeMatError", "El campo apellido materno no puede estar vacío.");
        }
        if (client.getPhone() == null || client.getPhone().isBlank()) {
            messages.put("phoneError", "El campo celular no puede estar vacío.");
        } else {
            if (client.getPhone().length() != 9) {
                messages.put("phoneError", "El número de celular debe tener de 9 digitos.");
            }
            try {
                int cel = Integer.parseInt(client.getPhone());
            } catch (Exception ex) {
                messages.put("phoneError", "Número de celular no válido.");
            }
        }
        if (client.getEmail() == null || client.getEmail().isBlank()) {
            messages.put("emailError", "El campo correo electronico no puede estar vacío.");
        } else {
            //Valida si el correo electronico tiene almenos un punto y solo un arroba
            int arroba = 0;
            int punto = 0;
            for (int i = 0; i < client.getEmail().length(); i++) {
                if (client.getEmail().charAt(i) == '@') {
                    arroba++;
                }
                if (client.getEmail().charAt(i) == '.') {
                    punto = 1;
                }
            }
            if (arroba != 1) {
                messages.put("emailError", "El formato del correo electrónico es inválido.");
            }
            if (punto == 0) {
                messages.put("emailError", "El formato del correo electrónico es inválido.");
            }
            //Valida si el email ya se encuentra registrado
            Optional<Client> clientOptional = this.clientService.findByEmailClient(client.getEmail().trim().toUpperCase());
            System.out.println("ID CLIENTE*************** " + client.getId());
            if (client.getId() == null) {
                clientOptional.ifPresent(u -> messages.put("emailError", "El correo electrónico ya se encuentra registrado."));
            } else {
                //Busca nuevamente si existe el correo electronico al editar
                if (clientOptional.isPresent()) {
                    if (!clientOptional.get().getId().equals(client.getId())) { //Comprueba si el correo pertenece al usuario a editar y da la contraria
                        clientOptional.ifPresent(u -> messages.put("emailError", "El correo electrónico ya se encuentra registrado."));
                    }
                }
            }
        }
        if (client.getDocumentType() == null) {
            messages.put("typeDocuError", "Debe seleccionar el tipo de documento.");
        }
        if (client.getNroDoc() == null || client.getNroDoc().isBlank()) {
            messages.put("nroDocuError", "El campo número de documento no puede estar vacío.");
        } else if (client.getNroDoc().length() > 15) {
            messages.put("nroDocuError", "Número de documento inválido.");
        }
        if (client.getDirection() == null || client.getDirection().isBlank()) {
            messages.put("directionError", "El campo número dirección no puede estar vacío.");
        }

        if (messages.isEmpty()) {
            Client clientSave = new Client();
            clientSave.setId(client.getId());
            clientSave.setCode(client.getCode());
            clientSave.setNames(client.getNames().trim().toUpperCase());
            clientSave.setApePat(client.getApePat().trim().toUpperCase());
            clientSave.setApeMat(client.getApeMat().trim().toUpperCase());
            clientSave.setNroDoc(client.getNroDoc().trim());
            clientSave.setDocumentType(client.getDocumentType());
            clientSave.setPhone(client.getPhone().trim());
            clientSave.setEmail(client.getEmail().trim().toUpperCase());
            clientSave.setDirection(client.getDirection().trim().toUpperCase());

            if (client.getId() != null && client.getId() > 0) {
                Optional<Client> optionalClient = this.clientService.findByIdClient(client.getId());
                if (optionalClient.isPresent()) {
                    Client c = optionalClient.get();
                    clientSave.getUserHistory().setState(c.getUserHistory().getState());
                    clientSave.getUserHistory().setUserReg(c.getUserHistory().getUserReg());
                    clientSave.getUserHistory().setDateReg(c.getUserHistory().getDateReg());
                    clientSave.getUserHistory().setUserMod("ADMIN_EDIT");
                }
            } else {
                clientSave.getUserHistory().setDateReg(client.getUserHistory().getDateReg());
                clientSave.getUserHistory().setUserReg("ADMIN".trim().toUpperCase());
            }

            this.clientService.saveClient(clientSave);
        }
        return messages;
    }
}
