package com.jfarro.app.controllers;

import com.jfarro.app.entities.Category;
import com.jfarro.app.entities.User;
import com.jfarro.app.services.ProductService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestScoped
public class CategoryProductController {

    @Inject
    private ProductService productService;

    public Map<String, String> dataValidationController(Category category, User userLogin) {
        Map<String, String> messages = new HashMap<>();
        if (category.getName() == null || category.getName().isBlank()) {
            messages.put("nameError", "El campo nombre de producto no puede estar vacío.");
        }

        if (messages.isEmpty()) {
            Category categorySave = new Category();
            categorySave.setId(category.getId());
            categorySave.setCode(category.getCode());
            categorySave.setName(category.getName());

            if (category.getId() != null && category.getId() > 0) {
                Optional<Category> optionalProduct = this.productService.findByIdCategory(category.getId());
                if (optionalProduct.isPresent()) {
                    Category p = optionalProduct.get();
                    categorySave.getUserHistory().setState(p.getUserHistory().getState());
                    categorySave.getUserHistory().setUserReg(p.getUserHistory().getUserReg());
                    categorySave.getUserHistory().setDateReg(p.getUserHistory().getDateReg());
                    categorySave.getUserHistory().setUserMod("ADMIN_EDIT");
                }

                this.productService.saveCategory(categorySave);
            } else {
                categorySave.getUserHistory().setDateReg(category.getUserHistory().getDateReg());
                categorySave.getUserHistory().setUserReg("ADMIN".trim().toUpperCase());
            }
        }
        return messages;
    }
}
