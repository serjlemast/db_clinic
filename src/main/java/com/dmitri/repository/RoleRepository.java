package com.dmitri.repository;

import com.dmitri.model.Role;

import java.util.List;

public interface RoleRepository {

    List<Role> getAllRoles();

    int getLastRoleId();

    int deleteRole(int id);

    int addNewRole(String name);

    int updateRole(Role role);

}