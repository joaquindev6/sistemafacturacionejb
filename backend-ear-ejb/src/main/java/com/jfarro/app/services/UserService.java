package com.jfarro.app.services;

import com.jfarro.app.entities.Role;
import com.jfarro.app.entities.User;
import jakarta.ejb.Local;

import java.util.List;
import java.util.Optional;

@Local
public interface UserService {

    List<User> findAllUsers();
    Optional<User> findByIdUser(Long id);
    void saveUser(User user);
    void deleteUser(Long id);

    List<Role> findAllRoles();
    Optional<Role> findByIdRole(Long id);
    void saveRole(Role role);
    void deleteRole(Long id);
}
