package com.ubb.mirko.concorde.controller;

import com.ubb.mirko.concorde.model.User;
import com.ubb.mirko.concorde.repository.UserRepository;
import com.ubb.mirko.concorde.repository.UserRepositoryWithRoom;

/**
 * Created by mirko on 02/01/2018.
 */

public class UserController {
    private static final UserController ourInstance = new UserController(new UserRepositoryWithRoom());
    private UserRepository repository_;
    private User currentUser = null;

    public static UserController getInstance() {
        return ourInstance;
    }

    public UserController(UserRepository repository) {
        repository_ = repository;
        // repository_.add(new User("ibis", "ibis", true));
        // repository_.add(new User("sparrow", "sparrow", false));
    }

    public User authenticate(String username, String password) {
        User user = repository_.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return user;
        }
        return null;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
