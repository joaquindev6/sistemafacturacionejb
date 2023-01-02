package com.jfarro.app.controllers;

import com.jfarro.app.entities.Role;
import com.jfarro.app.entities.User;
import com.jfarro.app.services.UserService;
import com.jfarro.app.util.CodeFormating;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet({"/usuarios"})
public class UserServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Inject
    private CodeFormating codeFormating;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Mostrando datos en la tabla y el formulario
        List<User> users = this.userService.findAllUsers();
        req.setAttribute("users", users);

        List<Role> roles = this.userService.findAllRoles();
        req.setAttribute("roles", roles);

        String code = "";

        //Verifica si se ha seleccionado el usuario para editar sus datos
        if (req.getParameter("id") != null) {
            long id;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (Exception ex) {
                id = 0;
            }
            Optional<User> optionalUser = this.userService.findByIdUser(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                code = user.getCode();
                req.setAttribute("user", user);
                req.getSession().setAttribute("userEdit", user);
            }
        } else {
            req.getSession().removeAttribute("userEdit");
            code = this.codeFormating.userCode();
        }

        //Verifica si se selecciono el usuario a eliminar por id
        if (req.getParameter("idDelete") != null) {
            long id;
            try {
                id = Long.parseLong(req.getParameter("idDelete"));
            } catch (Exception ex) {
                id = 0;
            }
            this.userService.deleteUser(id);
            resp.sendRedirect(req.getContextPath() + "/usuarios");
            return;
        }

        req.setAttribute("code", code);

        //Muestra los mensajes y datos
        if (req.getSession().getAttribute("messages") != null) {
            req.setAttribute("messages", req.getSession().getAttribute("messages"));
            req.getSession().removeAttribute("messages");
        }
        if (req.getSession().getAttribute("user") != null) {
            req.setAttribute("user", req.getSession().getAttribute("user"));
            req.getSession().removeAttribute("user");
        }

        getServletContext().getRequestDispatcher("/usuarios.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String names = req.getParameter("names");
        String apePat = req.getParameter("apePat");
        String apeMat = req.getParameter("apeMat");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String[] rolesIds = req.getParameterValues("roles");

        //Recupero el id del usuario seleccionado
        User userEdit = null;
        if (req.getSession().getAttribute("userEdit") != null) {
            userEdit = (User) req.getSession().getAttribute("userEdit");
            req.getSession().removeAttribute("userEdit");
        }

        //Validando los campos del formulario
        Map<String, String> messages = new HashMap<>();
        if (names == null || names.isBlank()) {
            messages.put("namesError", "El campo nombres no puede estar vacío.");
        }
        if (apePat == null || apePat.isBlank()) {
            messages.put("apePatError", "El campo apellido paterno no puede estar vacío.");
        }
        if (apeMat == null || apeMat.isBlank()) {
            messages.put("apeMatError", "El campo apellido materno no puede estar vacío.");
        }
        if (phone == null || phone.isBlank()) {
            messages.put("phoneError", "El campo celular no puede estar vacío.");
        } else {
            if (phone.length() != 9) {
                messages.put("phoneError", "El número de celular debe tener de 9 digitos.");
            }
            try {
                int cel = Integer.parseInt(phone);
            } catch (Exception ex) {
                messages.put("phoneError", "Número de celular no válido.");
            }
        }
        if (email == null || email.isBlank()) {
            messages.put("emailError", "El campo correo electronico no puede estar vacío.");
        } else {
            //Valida si el correo electronico tiene almenos un punto y solo un arroba
            int arroba = 0;
            int punto = 0;
            for (int i = 0; i < email.length(); i++) {
                if (email.charAt(i) == '@') {
                    arroba++;
                }
                if (email.charAt(i) == '.') {
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
            Optional<User> userOptional = this.userService.findByEmailUser(email.trim().toUpperCase());
            if (userEdit == null) {
                userOptional.ifPresent(user -> messages.put("emailError", "El correo electrónico ya se encuentra registrado."));
            } else {
                //Busca nuevamente si existe el correo electronico al editar
                if (userOptional.isPresent()) {
                    if (!userOptional.get().getId().equals(userEdit.getId())) { //Comprueba si el correo pertenece al usuario a editar y da la contraria
                        userOptional.ifPresent(user -> messages.put("emailError", "El correo electrónico ya se encuentra registrado."));
                    }
                }
            }
        }
        if (username == null || username.isBlank()) {
            messages.put("usernameError", "El campo nombre de usuario no puede estar vacío.");
        } else {
            if (username.length() > 30) {
                messages.put("usernameError", "El nombre de usuario debe tener un máximo de 30 caracteres.");
            }
            //Valida si el nombre de usuario ya se encuentra registrado
            Optional<User> userOptional = this.userService.findByUsername(username);
            if (userEdit == null) {
                userOptional.ifPresent(user -> messages.put("usernameError", "El nombre de usuario ya se encuentra registrado."));
            } else {
                //Busca nuevamente si existe el nombre de usuario al editar
                if (userOptional.isPresent()) {
                    if (!userOptional.get().getId().equals(userEdit.getId())) {
                        messages.put("usernameError", "El nombre de usuario ya se encuentra registrado.");
                    }
                }
            }
        }
        if (password == null || password.isBlank()) {
            messages.put("passwordError", "El campo contraseña no puede estar vacío.");
        } else if (password.length() > 30) {
            messages.put("passwordError", "La contraseña debe tener un máximo de 30 caracteres.");
        }
        if (rolesIds == null) {
            messages.put("roleError", "Debe seleccionar el rol de usuario.");
        }

        //Para al mostrar los herrores se mantengan los datos
        User user = new User();
        user.setNames(userEdit != null ? userEdit.getNames() : names);
        user.setApePat(userEdit != null ? userEdit.getApePat() : apePat);
        user.setApeMat(userEdit != null ? userEdit.getApeMat() : apeMat);
        user.setPhone(userEdit != null ? userEdit.getPhone() : phone);
        user.setEmail(userEdit != null ? userEdit.getEmail() : email);
        user.setUsername(userEdit != null ? userEdit.getUsername() : username);
        user.setPassword(userEdit != null ? userEdit.getPassword() : password);

        //Verifica si no existe errores
        if (messages.isEmpty()) {

            if (userEdit != null) {
                user.setId(userEdit.getId());
                Optional<User> optionalUser = this.userService.findByIdUser(userEdit.getId());
                if (optionalUser.isPresent()) {
                    User u = optionalUser.get();
                    user.getUserHistory().setState(u.getUserHistory().getState());
                    user.getUserHistory().setUserReg(u.getUserHistory().getUserReg());
                    user.getUserHistory().setDateReg(u.getUserHistory().getDateReg());
                    user.getUserHistory().setUserMod("ADMIN_EDIT");
                }
            }

            user.setCode(code);
            user.setNames(names.trim().toUpperCase());
            user.setApePat(apePat.trim().toUpperCase());
            user.setApeMat(apeMat.trim().toUpperCase());
            user.setPhone(phone.trim().toUpperCase());
            user.setEmail(email.trim().toUpperCase());
            user.setUsername(username);
            user.setPassword(password);
            user.getUserHistory().setUserReg("ADMIN".trim().toUpperCase());

            for (String ids: rolesIds) {
                long idRol = Long.parseLong(ids);
                System.out.println("roles******************: " + idRol);
                Optional<Role> optionalRole = this.userService.findByIdRole(idRol);
                optionalRole.ifPresent(role -> user.getRoles().add(role));
            }

            this.userService.saveUser(user);
            messages.put("exito", "Usuario guardado exitosamente");
            req.getSession().setAttribute("messages", messages);
            resp.sendRedirect(req.getContextPath() + "/usuarios");
        } else {
            req.getSession().setAttribute("messages", messages);
            req.getSession().setAttribute("user", user);
            //Si se esta editando paso el id por parametro
            if (userEdit != null) {
                resp.sendRedirect(req.getContextPath() + "/usuarios?id=" + userEdit.getId());
            } else {
                resp.sendRedirect(req.getContextPath() + "/usuarios");
            }
        }
    }
}
