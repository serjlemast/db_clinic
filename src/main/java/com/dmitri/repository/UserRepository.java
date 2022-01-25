package com.dmitri.repository;

import com.dmitri.model.User;
import com.dmitri.model.UserCredential;

import java.util.List;

public interface UserRepository {

    List<User> getUsers(int offset);

    int getLastUserId();

    int deleteUser(int id);

    int createNewUser(User user);

    int updateUser(User user);

    int getCountOfUsers();

    UserCredential findUserByUserNameAndPassword(String username, String password);
}
