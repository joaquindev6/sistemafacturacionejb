package com.jfarro.app.controllers;

import com.jfarro.app.entities.Product;
import com.jfarro.app.entities.User;
import com.jfarro.app.services.ProductService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestScoped
public class ProductController {

    @Inject
    private ProductService productService;

    public Map<String, String> dataValidationController(Product product, User userLogin) {
        //Validando los campos del formulario
        Map<String, String> messages = new HashMap<>();
        if (product.getName() == null || product.getName().isBlank()) {
            messages.put("nameError", "El campo nombre de producto no puede estar vacío.");
        }
        if (product.getAmount() > 0) {
            messages.put("amountError", "El campo cantidad no puede estar vacío.");
        }
        if (product.getPrice() > 0) {
            messages.put("priceError", "El campo precio no puede estar vacío.");
        }
        if (product.getCategory() == null) {
            messages.put("categoryError", "Debe de seleccionar una categoría.");
        }

        if (messages.isEmpty()) {
            Product productSave = new Product();
            productSave.setId(product.getId());
            productSave.setCode(product.getCode());
            productSave.setName(product.getName().trim().toUpperCase());
            productSave.setAmount(product.getAmount());
            productSave.setPrice(product.getPrice());
            productSave.setCategory(product.getCategory());

            if (product.getId() != null && product.getId() > 0) {
                Optional<Product> optionalProduct = this.productService.findByIdProduct(product.getId());
                if (optionalProduct.isPresent()) {
                    Product p = optionalProduct.get();
                    productSave.getUserHistory().setState(p.getUserHistory().getState());
                    productSave.getUserHistory().setUserReg(p.getUserHistory().getUserReg());
                    productSave.getUserHistory().setDateReg(p.getUserHistory().getDateReg());
                    productSave.getUserHistory().setUserMod("ADMIN_EDIT");
                }
            } else {
                productSave.getUserHistory().setDateReg(product.getUserHistory().getDateReg());
                productSave.getUserHistory().setUserReg("ADMIN".trim().toUpperCase());
            }

            this.productService.saveProduct(productSave);
        }
        return messages;
    }
}
