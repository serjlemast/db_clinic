package com.dmitri.service;

import com.dmitri.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers(int offset);

    User createUser(User user);

    void updateUser(User user);

    /**
     * Delete role from dataBase by role id
     *
     * @param userId - id of role that will be delete
     */
    void deleteUser(int userId);

    int getCountOfUser();
}
