package com.jfarro.app.controllers;

import com.jfarro.app.entities.Role;
import com.jfarro.app.services.UserService;
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

@WebServlet("/usuarios/roles")
public class RoleServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Inject
    private RoleController roleController;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Role> roles = this.userService.findAllRoles();
        req.setAttribute("roles", roles);

        //Verifica si se ha seleccionado el rol de usuario para editar sus datos
        if (req.getParameter("id") != null) {
            long id;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (Exception ex) {
                id = 0;
            }
            Optional<Role> roleOptional = this.userService.findByIdRole(id);
            if (roleOptional.isPresent()) {
                Role role = roleOptional.get();
                req.setAttribute("role", role);
                req.getSession().setAttribute("roleEdit", role);
            }
        }

        //Verifica si se selecciono el usuario a eliminar por id
        if (req.getParameter("idDelete") != null) {
            long id;
            try {
                id = Long.parseLong(req.getParameter("idDelete"));
            } catch (Exception ex) {
                id = 0;
            }
            this.userService.deleteRole(id);
            resp.sendRedirect(req.getContextPath() + "/usuarios/roles");
            return;
        }

        //Muestra los mensajes y datos
        if (req.getSession().getAttribute("messages") != null) {
            req.setAttribute("messages", req.getSession().getAttribute("messages"));
            req.getSession().removeAttribute("messages");
        }
        if (req.getSession().getAttribute("role") != null) {
            req.setAttribute("role", req.getSession().getAttribute("role"));
            req.getSession().removeAttribute("role");
        }

        getServletContext().getRequestDispatcher("/usuario-roles.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        //Recupero el id del usuario seleccionado
        Role roleEdit = null;
        if (req.getSession().getAttribute("roleEdit") != null) {
            roleEdit = (Role) req.getSession().getAttribute("roleEdit");
            req.getSession().removeAttribute("roleEdit");
        }

        Role role = new Role();
        role.setId(roleEdit != null ? roleEdit.getId() : null);
        role.setName(name);
        role.setDescription(description);

        Map<String, String> messages = this.roleController.dataValidationController(role, null);
        if (messages.isEmpty()) {
            messages.put("exito", "Rol de usuario guardado exitosamente");
            req.getSession().removeAttribute("roleEdit");
            req.getSession().setAttribute("messages", messages);
            resp.sendRedirect(req.getContextPath() + "/usuarios/roles");
        } else {
            req.getSession().setAttribute("messages", messages);
            req.getSession().setAttribute("role", role);
            //Si se esta editando paso el id por parametro
            if (roleEdit != null) {
                resp.sendRedirect(req.getContextPath() + "/usuarios/roles?id=" + roleEdit.getId());
            } else {
                resp.sendRedirect(req.getContextPath() + "/usuarios/roles");
            }
        }
    }
}
