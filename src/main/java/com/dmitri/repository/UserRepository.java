package com.dmitri.repository;

import com.dmitri.model.Role;
import com.dmitri.model.User;

import java.util.List;

public interface UserRepository {

    List<User> getUsers(int offset);

    User getUserById(int id);

    int getLastUserId();

    int deleteUser(int id);

    int createNewUser(User user);

    int updateUser(User user);

    int getCountOfUsers();
}
