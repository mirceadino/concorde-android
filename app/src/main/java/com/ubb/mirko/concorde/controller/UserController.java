package com.ubb.mirko.concorde.controller;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.ubb.mirko.concorde.model.User;
import com.ubb.mirko.concorde.repository.UserRepository;
import com.ubb.mirko.concorde.repository.UserRepositoryWithRoom;

/**
 * Created by mirko on 02/01/2018.
 */

public class UserController {
    private static final UserController ourInstance = new UserController(new UserRepositoryWithRoom());
    private UserRepository repository;
    private User currentUser = null;

    public static UserController getInstance() {
        return ourInstance;
    }

    public UserController(UserRepository repository) {
        this.repository = repository;
        System.out.println(repository.get());
    }

    public User authenticate(String username, String password) {
        User user = repository.get(username);
        if (user != null && !user.getPassword().equals(password)) {
            user = null;
        }
        currentUser = user;
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User authenticateWithGoogle(GoogleSignInAccount account) {
        User user = repository.get(account.getId());
        if (user == null) {
            String username = account.getId();
            String password = account.getId();
            user = new User(username, password, false);
            repository.add(user);
        }
        currentUser = user;
        return currentUser;
    }
}
