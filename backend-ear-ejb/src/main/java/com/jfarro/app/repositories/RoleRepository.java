package com.jfarro.app.repositories;

import com.jfarro.app.entities.Role;

public interface RoleRepository extends CrudRepository<Role> {
    Role findByName(String name);
}
