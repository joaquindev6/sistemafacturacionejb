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
public class UserController {

    @Inject
    private UserService userService;

    public Map<String, String> dataValidationController(User user, String[] rolesIds, User userLogin) {
        //Validando los campos del formulario
        Map<String, String> messages = new HashMap<>();
        if (user.getNames() == null || user.getNames().isBlank()) {
            messages.put("namesError", "El campo nombres no puede estar vacío.");
        }
        if (user.getApePat() == null || user.getApePat().isBlank()) {
            messages.put("apePatError", "El campo apellido paterno no puede estar vacío.");
        }
        if (user.getApeMat() == null || user.getApeMat().isBlank()) {
            messages.put("apeMatError", "El campo apellido materno no puede estar vacío.");
        }
        if (user.getPhone() == null || user.getPhone().isBlank()) {
            messages.put("phoneError", "El campo celular no puede estar vacío.");
        } else {
            if (user.getPhone().length() != 9) {
                messages.put("phoneError", "El número de celular debe tener de 9 digitos.");
            }
            try {
                int cel = Integer.parseInt(user.getPhone());
            } catch (Exception ex) {
                messages.put("phoneError", "Número de celular no válido.");
            }
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            messages.put("emailError", "El campo correo electronico no puede estar vacío.");
        } else {
            //Valida si el correo electronico tiene almenos un punto y solo un arroba
            int arroba = 0;
            int punto = 0;
            for (int i = 0; i < user.getEmail().length(); i++) {
                if (user.getEmail().charAt(i) == '@') {
                    arroba++;
                }
                if (user.getEmail().charAt(i) == '.') {
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
            Optional<User> userOptional = this.userService.findByEmailUser(user.getEmail().trim().toUpperCase());
            if (user.getId() == null) {
                userOptional.ifPresent(u -> messages.put("emailError", "El correo electrónico ya se encuentra registrado."));
            } else {
                //Busca nuevamente si existe el correo electronico al editar
                if (userOptional.isPresent()) {
                    if (!userOptional.get().getId().equals(user.getId())) { //Comprueba si el correo pertenece al usuario a editar y da la contraria
                        userOptional.ifPresent(u -> messages.put("emailError", "El correo electrónico ya se encuentra registrado."));
                    }
                }
            }
        }
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            messages.put("usernameError", "El campo nombre de usuario no puede estar vacío.");
        } else {
            if (user.getUsername().length() > 30) {
                messages.put("usernameError", "El nombre de usuario debe tener un máximo de 30 caracteres.");
            }
            //Valida si el nombre de usuario ya se encuentra registrado
            Optional<User> userOptional = this.userService.findByUsername(user.getUsername());
            if (user.getId() == null) {
                userOptional.ifPresent(u -> messages.put("usernameError", "El nombre de usuario ya se encuentra registrado."));
            } else {
                //Busca nuevamente si existe el nombre de usuario al editar
                if (userOptional.isPresent()) {
                    if (!userOptional.get().getId().equals(user.getId())) {
                        messages.put("usernameError", "El nombre de usuario ya se encuentra registrado.");
                    }
                }
            }
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            messages.put("passwordError", "El campo contraseña no puede estar vacío.");
        } else if (user.getPassword().length() > 30) {
            messages.put("passwordError", "La contraseña debe tener un máximo de 30 caracteres.");
        }
        if (rolesIds == null) {
            messages.put("roleError", "Debe seleccionar el rol de usuario.");
        }

        if (messages.isEmpty()) {

            User userSave = new User();
            userSave.setId(user.getId());
            userSave.setCode(user.getCode());
            userSave.setNames(user.getNames().trim().toUpperCase());
            userSave.setApePat(user.getApePat().trim().toUpperCase());
            userSave.setApeMat(user.getApeMat().trim().toUpperCase());
            userSave.setPhone(user.getPhone().trim().toUpperCase());
            userSave.setEmail(user.getEmail().trim().toUpperCase());
            userSave.setUsername(user.getUsername());
            userSave.setPassword(user.getPassword());

            for (String ids: rolesIds) {
                long idRol = Long.parseLong(ids);
                System.out.println("roles******************: " + idRol);
                Optional<Role> optionalRole = this.userService.findByIdRole(idRol);
                optionalRole.ifPresent(role -> userSave.getRoles().add(role));
            }

            if (user.getId() != null && user.getId() > 0) {
                Optional<User> optionalUser = this.userService.findByIdUser(user.getId());
                if (optionalUser.isPresent()) {
                    User u = optionalUser.get();
                    userSave.getUserHistory().setState(u.getUserHistory().getState());
                    userSave.getUserHistory().setUserReg(u.getUserHistory().getUserReg());
                    userSave.getUserHistory().setDateReg(u.getUserHistory().getDateReg());
                    userSave.getUserHistory().setUserMod("ADMIN_EDIT");
                }
            } else {
                userSave.getUserHistory().setDateReg(user.getUserHistory().getDateReg());
                userSave.getUserHistory().setUserReg("ADMIN".trim().toUpperCase());
            }

            this.userService.saveUser(userSave);
        }
        return messages;
    }
}
