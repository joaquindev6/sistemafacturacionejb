package com.jfarro.app.services.impl;

import com.jfarro.app.entities.Category;
import com.jfarro.app.entities.Product;
import com.jfarro.app.repositories.CategoryRepository;
import com.jfarro.app.repositories.ProductRepository;
import com.jfarro.app.services.ProductService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@Stateless
public class ProductServiceImpl implements ProductService {

    @Inject
    private ProductRepository productRepository;

    @Inject
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> findAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Optional<Product> findByIdProduct(Long id) {
        return Optional.ofNullable(this.productRepository.findById(id));
    }

    @Override
    public void saveProduct(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        this.productRepository.delete(id);
    }

    @Override
    public List<Category> findAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findByIdCategory(Long id) {
        return Optional.ofNullable(this.categoryRepository.findById(id));
    }

    @Override
    public void saveCategory(Category category) {
        this.categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        this.categoryRepository.delete(id);
    }
}
