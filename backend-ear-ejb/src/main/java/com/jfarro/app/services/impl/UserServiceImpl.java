package com.jfarro.app.services.impl;

import com.jfarro.app.entities.Role;
import com.jfarro.app.entities.User;
import com.jfarro.app.repositories.RoleRepository;
import com.jfarro.app.repositories.UserRepository;
import com.jfarro.app.services.UserService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@Stateless
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private RoleRepository roleRepository;

    @Override
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findByIdUser(Long id) {
        return Optional.ofNullable(this.userRepository.findById(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(this.userRepository.findByUsername(username));
    }

    @Override
    public Optional<User> findByEmailUser(String email) {
        return Optional.ofNullable(this.userRepository.findByEmail(email));
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.delete(id);
    }

    @Override
    public List<Role> findAllRoles() {
        return this.roleRepository.findAll();
    }

    @Override
    public Optional<Role> findByIdRole(Long id) {
        return Optional.ofNullable(this.roleRepository.findById(id));
    }

    @Override
    public void saveRole(Role role) {
        this.roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        this.roleRepository.delete(id);
    }
}
