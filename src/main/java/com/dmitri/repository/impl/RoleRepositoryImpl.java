package com.dmitri.repository.impl;

import com.dmitri.exeption.ApplicationException;
import com.dmitri.model.Role;
import com.dmitri.repository.RoleRepository;
import com.dmitri.utils.MyDataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.dmitri.constant.WebConstant.*;

public class RoleRepositoryImpl implements RoleRepository {

    /**
     * Role column names: 'id' and 'name'
     */
    private static final String ROLE_ID_COLUMN = "id";
    public static final String ROLE_NAME_COLUMN = "name";

    /**
     * Connection to MSQL data base
     */
    private final Connection connection;

    public RoleRepositoryImpl() {
        connection = MyDataBaseConnection.getInstance();
    }


    @Override
    public List<Role> getAllRoles() {
        List<Role> roleList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_ROLES_SQL_QUERY)) {
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt(ROLE_ID_COLUMN));
                role.setName(resultSet.getString(ROLE_NAME_COLUMN));
                roleList.add(role);
            }
        } catch (SQLException ex) {
            throw new ApplicationException("Error getting roles from database, error: " + ex.getMessage());
        }
        return roleList;
    }


    @Override
    public int deleteRole(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE_BY_ID_SQL_QUERY)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new ApplicationException("Error deleting role by id from database, error: " + ex.getMessage());
        }
    }


    @Override
    public int addNewRole(String name) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_ROLE_SQL_QUERY)) {
            preparedStatement.setString(1, name);
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new ApplicationException("Error inserting new role to database, error: " + ex.getMessage());
        }
    }


    @Override
    public int updateRole(Role role) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE_NAME_BY_ROLE_ID_SQL_QUERY)) {
            preparedStatement.setString(1, role.getName());
            preparedStatement.setInt(2, role.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new ApplicationException("Error updating role name by id, error: " + ex.getMessage());
        }
    }


    @Override
    public int getLastRoleId() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LAST_ROLE_ID_SQL_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(ROLE_ID_COLUMN);
            } else {
                throw new ApplicationException("Not found role by last id");
            }
        } catch (SQLException ex) {
            throw new ApplicationException("Error getting last role id by max id, error: " + ex.getMessage());
        }
    }
}
