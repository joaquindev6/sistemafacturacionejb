package com.jfarro.app.repositories;

import java.util.List;

public interface CrudRepository<T> {
    List<T> findAll();
    T findById(T t);
    void save(T t);
    void delete(Long id);
}
