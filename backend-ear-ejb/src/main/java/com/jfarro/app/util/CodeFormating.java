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
        List<User> users = this.userService.findAllUsers();
        long idFinal = users.get(users.size() - 1).getId();
        return "USU-" + String.format("%6d", idFinal).replace(" ", "0");
    }

    public String clientCode() {
        List<Client> clients = this.clientService.findAllClients();
        long idFinal = clients.get(clients.size() - 1).getId();
        return "CLI-" + String.format("%6d", idFinal).replace(" ", "0");
    }

    public String productCode() {
        List<Product> products = this.productService.findAllProducts();
        long idFinal = products.get(products.size() - 1).getId();
        return "PRO-" + String.format("%6d", idFinal).replace(" ", "0");
    }

    public String categoryProductCode() {
        List<Category> categories = this.productService.findAllCategories();
        long idFinal = categories.get(categories.size() - 1).getId();
        return "PRO-" + String.format("%6d", idFinal).replace(" ", "0");
    }
}
