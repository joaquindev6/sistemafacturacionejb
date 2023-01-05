package com.jfarro.app.controllers;

import com.jfarro.app.entities.Client;
import com.jfarro.app.entities.DocumentType;
import com.jfarro.app.services.ClientService;
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

@WebServlet("/clientes")
public class ClientServlet extends HttpServlet {

    @Inject
    private ClientService clientService;

    @Inject
    private ClientController clientController;

    @Inject
    private CodeFormating codeFormating;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Client> clients = this.clientService.findAllClients();
        req.setAttribute("clients", clients);

        List<DocumentType> documentTypes = this.clientService.findAllDocumentTypes();
        req.setAttribute("documentTypes", documentTypes);

        String code = "";

        //Verifica si se ha seleccionado el usuario para editar sus datos
        if (req.getParameter("id") != null) {
            long id;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (Exception ex) {
                id = 0;
            }
            Optional<Client> optionalUser = this.clientService.findByIdClient(id);
            if (optionalUser.isPresent()) {
                Client client = optionalUser.get();
                code = client.getCode();
                req.setAttribute("client", client);
                req.getSession().setAttribute("clientEdit", client);
            }
        } else {
            code = this.codeFormating.clientCode();
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
            this.clientService.deleteClient(id);
            resp.sendRedirect(req.getContextPath() + "/clientes");
            return;
        }

        //Muestra los mensajes y datos
        if (req.getSession().getAttribute("messages") != null) {
            req.setAttribute("messages", req.getSession().getAttribute("messages"));
            req.getSession().removeAttribute("messages");
        }
        if (req.getSession().getAttribute("client") != null) {
            req.setAttribute("client", req.getSession().getAttribute("client"));
            req.getSession().removeAttribute("client");
        }

        getServletContext().getRequestDispatcher("/clientes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String names = req.getParameter("names");
        String apePat = req.getParameter("apePat");
        String apeMat = req.getParameter("apeMat");
        String nroDocu = req.getParameter("nroDocu");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String direction = req.getParameter("direction");

        long idTypeDocu;
        try {
            idTypeDocu = Long.parseLong(req.getParameter("typeDocu"));
        } catch (Exception ex) {
            idTypeDocu = 0;
        }

        //Recupero el id del usuario seleccionado
        Client clientEdit = null;
        if (req.getSession().getAttribute("clientEdit") != null) {
            clientEdit = (Client) req.getSession().getAttribute("clientEdit");
            req.getSession().removeAttribute("clientEdit");
        }

        Client client = new Client();
        client.setId(clientEdit != null ? clientEdit.getId() : null);
        client.setCode(code);
        client.setNames(names);
        client.setApePat(apePat);
        client.setApeMat(apeMat);

        Optional<DocumentType> optional = this.clientService.findByIdDocumentType(idTypeDocu);
        optional.ifPresent(client::setDocumentType);

        client.setNroDoc(nroDocu);
        client.setPhone(phone);
        client.setEmail(email);
        client.setDirection(direction);

        Map<String, String> messages = this.clientController.dataValidationController(client, null);
        if (messages.isEmpty()) {
            messages.put("exito", "Cliente guardado exitosamente");
            req.getSession().setAttribute("messages", messages);
            resp.sendRedirect(req.getContextPath() + "/clientes");
        } else {
            req.getSession().setAttribute("messages", messages);
            req.getSession().setAttribute("client", client);
            //Si se esta editando paso el id por parametro
            if (clientEdit != null) {
                resp.sendRedirect(req.getContextPath() + "/clientes?id=" + clientEdit.getId());
            } else {
                resp.sendRedirect(req.getContextPath() + "/clientes");
            }
        }
    }
}
