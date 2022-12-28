package com.jfarro.app.services;

import com.jfarro.app.entities.Category;
import com.jfarro.app.entities.Product;
import jakarta.ejb.Local;

import java.util.List;
import java.util.Optional;

@Local
public interface ProductService {
    List<Product> findAllProducts();
    Optional<Product> findByIdProduct(Long id);
    void saveProduct(Product product);
    void deleteProduct(Long id);

    List<Category> findAllCategories();
    Optional<Category> findByIdCategory(Long id);
    void saveCategory(Category category);
    void deleteCategory(Long id);
}
