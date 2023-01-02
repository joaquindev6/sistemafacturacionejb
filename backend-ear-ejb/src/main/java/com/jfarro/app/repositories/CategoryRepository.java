package com.jfarro.app.repositories;

import com.jfarro.app.entities.Category;

public interface CategoryRepository extends CrudRepository<Category> {
    Category findByName(String name);
}
