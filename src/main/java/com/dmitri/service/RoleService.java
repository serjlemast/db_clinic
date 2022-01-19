package com.dmitri.service;

import com.dmitri.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role createRole(String name);

    void updateRole(Role role);

    /**
     * Delete role from dataBase by role id
     *
     * @param roleId - id of role that will be delete
     */
    void deleteRole(int roleId);

}
