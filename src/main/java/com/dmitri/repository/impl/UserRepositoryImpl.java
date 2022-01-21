package com.dmitri.repository.impl;

import com.dmitri.exeption.ApplicationException;
import com.dmitri.model.User;
import com.dmitri.repository.UserRepository;
import com.dmitri.utils.MyDataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.dmitri.constant.WebConstant.DEFAULT_COUNT_OF_USERS_ON_PAGE;

public class UserRepositoryImpl implements UserRepository {

    /**
     * SQL queries
     */
    private static String SELECT_USERS_SQL_QUERY = "SELECT u.id, first_name, second_name, phone, birthday, username," +
            " password, r.name FROM users AS u LEFT JOIN role AS r ON role_id=r.id LIMIT 5 OFFSET ";
    private static final String INSERT_NEW_USER_SQL_QUERY = "INSERT INTO users (second_name,birthday,first_name,phone," +
            "room_number,password,username,role_id) VALUES (?,?,?,?,?,?,?,?)";
    private static final String DELETE_USER_BY_ID_SQL_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_LAST_USER_ID_SQL_QUERY = "SELECT MAX(id) AS id FROM users";
    private static final String SELECT_COUNT_OF_USERS_SQL_QUERY = "SELECT COUNT(id) FROM users";

    /**
     * User column
     */
    private static final String USER_ID_COLUMN = "id";
    public static final String USER_FIRST_NAME_COLUMN = "first_name";
    public static final String USER_SECOND_NAME_COLUMN = "second_name";
    public static final String USER_PHONE_COLUMN = "phone";
    public static final String USER_BIRTHDAY_COLUMN = "birthday";
    public static final String USER_NAME_COLUMN = "username";
    public static final String USER_PASSWORD_COLUMN = "password";
    public static final String USER_ROLE_NAME_COLUMN = "r.name";

    /**
     * Connection to MSQL data base
     */
    private final Connection connection;

    public UserRepositoryImpl() {
        connection = MyDataBaseConnection.getInstance();
    }

    @Override
    public List<User> getUsers(int offset) {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_USERS_SQL_QUERY + calculateOffset(offset))) {
            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getInt(USER_ID_COLUMN))
                        .firstName(resultSet.getString(USER_FIRST_NAME_COLUMN))
                        .secondName(resultSet.getString(USER_SECOND_NAME_COLUMN))
                        .phone(resultSet.getString(USER_PHONE_COLUMN))
                        .password(resultSet.getString(USER_PASSWORD_COLUMN))
                        .username(resultSet.getString(USER_NAME_COLUMN))
                        .birthday(resultSet.getDate(USER_BIRTHDAY_COLUMN))
                        .roleName(resultSet.getString(USER_ROLE_NAME_COLUMN))
                        .build();
                userList.add(user);
            }
        } catch (SQLException ex) {
            throw new ApplicationException("Error getting users from database, error:", ex);
        }
        return userList;
    }

    private int calculateOffset(int offset) {
        if (offset < 1) {
            return 1;
        }
        return (offset - 1) * DEFAULT_COUNT_OF_USERS_ON_PAGE;
    }

    @Override
    public int getLastUserId() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LAST_USER_ID_SQL_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(USER_ID_COLUMN);
            } else {
                throw new ApplicationException("Not found user by last id");
            }
        } catch (SQLException ex) {
            throw new ApplicationException("Error getting last user id by max id, error:", ex);
        }
    }

    @Override
    public int deleteUser(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID_SQL_QUERY)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new ApplicationException("Error deleting USER by id from database, error:", ex);
        }
    }

    @Override
    public int createNewUser(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_USER_SQL_QUERY)) {
            preparedStatement.setString(1, user.getSecondName());
            preparedStatement.setDate(2, (Date) user.getBirthday());
            preparedStatement.setString(3, user.getSecondName());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getRoomNumber());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setString(7, user.getUsername());
            preparedStatement.setInt(8, getRoleId(user.getRoleName()));
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new ApplicationException("Error inserting new user to database, error:", ex);
        }
    }

    @Override
    public int updateUser(User user) {
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(createCustomSql(user));
        } catch (SQLException ex) {
            throw new ApplicationException("Error updating role name by id, error:", ex);
        }
    }

    @Override
    public int getCountOfUsers() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_COUNT_OF_USERS_SQL_QUERY);
            if (resultSet.next()) {
                return resultSet.getInt("COUNT(id)");
            } else {
                throw new ApplicationException("Not found user by last id");
            }
        } catch (SQLException ex) {
            throw new ApplicationException("Error getting count of users, error:", ex);
        }
    }

    private String createCustomSql(User user) {
        StringBuilder builder = new StringBuilder()
                .append("username = '" + user.getUsername() + "'")
                .append(",")
                .append("second_name = '" + user.getSecondName() + "'");
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            builder
                    .append(",")
                    .append("password = '" + user.getPassword() + "'");
        }
        if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
            builder
                    .append(",")
                    .append("first_name = '" + user.getFirstName() + "'");
        }
        if (user.getPhone() != null && !user.getPhone().isEmpty()) {
            builder
                    .append(",")
                    .append("phone = '" + user.getPhone() + "'");

        }
        if (user.getRoleName() != null && !user.getRoleName().isEmpty() && !user.getRoleName().equals("ROLE NAME")) {
            builder
                    .append(",")
                    .append("role_id = " + getRoleId(user.getRoleName()));
            //todo

        }

        String parameters = builder.toString();
        String templateSql = "UPDATE users SET %s WHERE id = %s";
        String result = String.format(templateSql, parameters, user.getId());
        return result;
    }

    private int getRoleId(String roleName) {
        String sql4 = "SELECT id FROM role WHERE name = '" + roleName + "'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql4)) {
            while (resultSet.next()) {
                return resultSet.getInt(USER_ID_COLUMN);
            }
            return 0;
        } catch (SQLException ex) {
            throw new ApplicationException("Error getting role name of role by roleName, error:", ex);
        }
    }
}
