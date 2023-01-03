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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet({"/usuarios"})
public class UserServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Inject
    private UserController userController;

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
            code = this.codeFormating.userCode();
        }
        req.setAttribute("code", code);

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

        User user = new User();
        user.setId(userEdit != null ? userEdit.getId() : null);
        user.setCode(code);
        user.setNames(names);
        user.setApePat(apePat);
        user.setApeMat(apeMat);
        user.setPhone(phone);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        Map<String, String> messages = this.userController.dataValidationController(user, rolesIds, null);
        if (messages.isEmpty()) {
            messages.put("exito", "Usuario guardado exitosamente");
            req.getSession().removeAttribute("userEdit");
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
