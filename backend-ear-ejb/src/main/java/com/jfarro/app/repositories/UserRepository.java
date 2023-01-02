package com.jfarro.app.repositories;

import com.jfarro.app.entities.User;

public interface UserRepository extends CrudRepository<User> {
    User findByUsername(String username);
    User findByEmail(String email);
}
