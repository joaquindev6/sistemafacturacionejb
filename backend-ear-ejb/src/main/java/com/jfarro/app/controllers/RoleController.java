package com.jfarro.app.controllers;

import com.jfarro.app.entities.Role;
import com.jfarro.app.entities.User;
import com.jfarro.app.services.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestScoped
public class RoleController {

    @Inject
    private UserService userService;

    public Map<String, String> dataValidationController(Role role, User userLogin) {
        //Validando los campos del formulario
        Map<String, String> messages = new HashMap<>();
        if (role.getName() == null || role.getName().isBlank()) {
            messages.put("nameError", "El campo nombre no puede estar vacío.");
        } else if (role.getName().length() > 30) {
            messages.put("nameError", "El campo nombre no puede tener más de 30 caracteres.");
        }
        if (role.getDescription() == null || role.getDescription().isBlank()) {
            messages.put("descriptionError", "El campo descripción no puede estar vacío.");
        } else if (role.getName().length() > 50) {
            messages.put("descriptionError", "El campo descripción no puede tener más de 30 caracteres.");
        }

        if (messages.isEmpty()) {
            Role roleSave = new Role();
            roleSave.setId(role.getId());
            roleSave.setName(role.getName().trim().toUpperCase());
            roleSave.setDescription(role.getDescription().trim().toUpperCase());

            if (role.getId() != null && role.getId() > 0) {
                Optional<Role> roleOptional = this.userService.findByIdRole(role.getId());
                if (roleOptional.isPresent()) {
                    Role r = roleOptional.get();
                    roleSave.getUserHistory().setState(r.getUserHistory().getState());
                    roleSave.getUserHistory().setUserReg(r.getUserHistory().getUserReg());
                    roleSave.getUserHistory().setDateReg(r.getUserHistory().getDateReg());
                    roleSave.getUserHistory().setUserMod("ADMIN_EDIT");
                }
            } else {
                roleSave.getUserHistory().setDateReg(role.getUserHistory().getDateReg());
                roleSave.getUserHistory().setUserReg("ADMIN".trim().toUpperCase());
            }

            this.userService.saveRole(roleSave);
        }
        return messages;
    }
}
