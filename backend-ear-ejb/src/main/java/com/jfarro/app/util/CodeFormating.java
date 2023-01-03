package com.jfarro.app.util;

import com.jfarro.app.entities.Category;
import com.jfarro.app.entities.Client;
import com.jfarro.app.entities.Product;
import com.jfarro.app.entities.User;
import com.jfarro.app.services.ClientService;
import com.jfarro.app.services.ProductService;
import com.jfarro.app.services.UserService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class CodeFormating {

    @Inject
    private UserService userService;

    @Inject
    private ClientService clientService;

    @Inject
    private ProductService productService;

    public String userCode() {
        String code = "";
        List<User> users = this.userService.findAllUsers();
        if (!users.isEmpty()) {
            long idFinal = users.get(users.size() - 1).getId() + 1;
            code = "USU-" + String.format("%6d", idFinal).replace(" ", "0");
        } else {
            code = "USU-000001";
        }
        return code;
    }

    public String clientCode() {
        String code = "";
        List<Client> clients = this.clientService.findAllClients();
        if (!clients.isEmpty()) {
            long idFinal = clients.get(clients.size() - 1).getId() + 1;
            code = "CLI-" + String.format("%6d", idFinal).replace(" ", "0");
        } else {
            code = "CLI-000001";
        }
        return code;
    }

    public String productCode() {
        String code = "";
        List<Product> products = this.productService.findAllProducts();
        if (!products.isEmpty()) {
            long idFinal = products.get(products.size() - 1).getId() + 1;
            code = "PRO-" + String.format("%6d", idFinal).replace(" ", "0");
        } else {
            code = "PRO-000001";
        }
        return code;
    }

    public String categoryProductCode() {
        String code = "";
        List<Category> categories = this.productService.findAllCategories();
        if (!categories.isEmpty()) {
            long idFinal = categories.get(categories.size() - 1).getId() + 1;
            code = "PRO-" + String.format("%6d", idFinal).replace(" ", "0");
        } else {
            code = "CAT-000001";
        }
        return code;
    }
}
