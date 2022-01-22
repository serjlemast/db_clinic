package com.dmitri.service.impl;

import com.dmitri.exeption.ApplicationException;
import com.dmitri.model.User;
import com.dmitri.repository.UserRepository;
import com.dmitri.repository.impl.UserRepositoryImpl;
import com.dmitri.service.UserService;
import com.dmitri.utils.MyDataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl() {
        userRepository = new UserRepositoryImpl();
    }


    @Override
    public List<User> getUsers(int offset) {
        List<User> allUsers = userRepository.getUsers(offset);
        if (allUsers.isEmpty()) {
            throw new ApplicationException("Not found any users in database");
        }
        return allUsers;
    }

    @Override
    public User createUser(User user) {
        int userId = getNewUserIdTx(user);
        user.setId(userId);
        userRepository.updateUserRoleIdByName(userId,user.getRoleName());
        return user;
    }

    @Override
    public void updateUser(User user) {
        int statusCode = userRepository.updateUser(user);
        int statusCode2 = userRepository.updateUserRoleIdByName(user.getId(),user.getRoleName());
        if (statusCode > 0 && statusCode2 >0) {
            return;
        }
        throw new ApplicationException("Cant update user: " + user);
    }

    @Override
    public void deleteUser(int userId) {
        int status = userRepository.deleteUser(userId);
        if (status > 0) {
            return;
        }
        throw new ApplicationException("Not found user by id: " + userId);
    }

    @Override
    public int getCountOfUser() {
        int status = userRepository.getCountOfUsers();
        if (status > 0) {
            return status;
        }
        throw new ApplicationException("Not found count of user");
    }

    private int getNewUserIdTx(User user) {
        Connection connection = MyDataBaseConnection.getInstance();
        try {
            // 1. start DB transaction
            connection.setAutoCommit(false);
            int statusCode = userRepository.createNewUser(user);
            if (statusCode <= 0) {
                throw new ApplicationException("New user was not created in the database, user: " + user);
            }
            int userId = userRepository.getLastUserId();
            // 2.commit DB transaction
            connection.commit();
            return userId;
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
}
