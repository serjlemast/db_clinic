package com.dmitri.service.impl;

import com.dmitri.exeption.ApplicationException;
import com.dmitri.model.Role;
import com.dmitri.repository.RoleRepository;
import com.dmitri.repository.impl.RoleRepositoryImpl;
import com.dmitri.service.RoleService;
import com.dmitri.utils.MyDataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl() {
        roleRepository = new RoleRepositoryImpl();
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> allRoles = roleRepository.getAllRoles();
        if (allRoles.isEmpty()) {
            throw new ApplicationException("Not found any roles in database");
        }
        return allRoles;
    }


    @Override
    public void deleteRole(int roleId) {
        int status = roleRepository.deleteRole(roleId);
        if (status > 0) {
            return;
        }
        throw new ApplicationException("Not found role by id: " + roleId);
    }


    @Override
    public Role createRole(String name) {
        int roleId = getNewRoleIdTx(name);
        return new Role(roleId, name);
    }


    private int getNewRoleIdTx(String name) {
        Connection connection = MyDataBaseConnection.getInstance();
        try {
            // 1. start DB transaction
            connection.setAutoCommit(false);
            int statusCode = roleRepository.addNewRole(name);
            if (statusCode <= 0) {
                throw new ApplicationException("New role was not created in the database, role name: " + name);
            }
            int roleId = roleRepository.getLastRoleId();
            // 2.commit DB transaction
            connection.commit();
            return roleId;
        } catch (Exception e) {
            try {
                // 3. rollback DB transaction
                connection.rollback();
            } catch (SQLException exception) {
                throw new ApplicationException(e.getMessage(), e);
            }
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    @Override
    public void updateRole(Role role) {
        int statusCode = roleRepository.updateRole(role);
        if (statusCode > 0) {
            return;
        }
        throw new ApplicationException("Cant update role: " + role);
    }
}