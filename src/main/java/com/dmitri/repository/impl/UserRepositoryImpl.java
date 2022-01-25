package com.dmitri.repository.impl;

import com.dmitri.exeption.ApplicationException;
import com.dmitri.model.User;
import com.dmitri.repository.UserRepository;
import com.dmitri.utils.MyDataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.dmitri.constant.WebConstant.*;

public class UserRepositoryImpl implements UserRepository {

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
    public static final String USER_ROLE_NAME_COLUMN = "role_name";

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
            throw new ApplicationException("Error getting users from database, error: " + ex.getMessage());
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
            throw new ApplicationException("Error getting last user id by max id, error: " + ex.getMessage());
        }
    }

    @Override
    public int deleteUser(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID_SQL_QUERY)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new ApplicationException("Error deleting USER by id from database, error: " + ex.getMessage());
        }
    }

    @Override
    public int createNewUser(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_USER_SQL_QUERY)) {
            preparedStatement.setString(1, user.getSecondName());
            preparedStatement.setDate(2, user.getBirthday());
            preparedStatement.setString(3, user.getSecondName());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getRoomNumber());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setString(7, user.getUsername());
            preparedStatement.setString(8, user.getRoleName());
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new ApplicationException("Error inserting new user to database, error: " + ex.getMessage());
        }
    }

    @Override
    public int updateUser(User user) {
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(createCustomSql(user));
        } catch (SQLException ex) {
            throw new ApplicationException("Error updating role name by id, error: " + ex.getMessage());
        }
    }

    @Override
    public int getCountOfUsers() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_COUNT_OF_USERS_SQL_QUERY);
            if (resultSet.next()) {
                return resultSet.getInt("count_id");
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            throw new ApplicationException("Error getting count of users, error: " + ex.getMessage());
        }
    }

    @Override
    public boolean findUserByParameters(String username,String password) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM users WHERE username = ? AND password = ?")) {
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
        } catch (SQLException ex) {
            throw new ApplicationException("Error finding user by username and password, error: " + ex.getMessage());
        }
    }

    private String createCustomSql(User user) {
        StringBuilder builder = new StringBuilder()
                .append("username = '" + user.getUsername() + "'")
                .append(",")
                .append("second_name = '" + user.getSecondName() + "'");
        String password = user.getPassword();
        if (password != null && !password.isEmpty()) {
            builder
                    .append(",")
                    .append("password = '" + password + "'");
        }
        String firstName = user.getFirstName();
        if (firstName != null && !firstName.isEmpty()) {
            builder
                    .append(",")
                    .append("first_name = '" + firstName + "'");
        }
        String phone = user.getPhone();
        if (phone != null && !phone.isEmpty()) {
            builder
                    .append(",")
                    .append("phone = '" + phone + "'");
        }
        Date birthday = user.getBirthday();
        if (birthday != null) {
            builder
                    .append(",")
                    .append("birthday = '" + birthday + "'");
        }
        String roleName = user.getRoleName();
        if (roleName != null && roleName.isEmpty()) {
            builder
                    .append(",")
                    .append("role_id = (SELECT id FROM role WHERE name = " + roleName + ")");
        }
        return String.format(UPDATE_USER_TEMPLATE_SQL_QUERY, builder.toString(), user.getId());
    }
}
