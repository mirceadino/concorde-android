package com.ubb.mirko.concorde.controller;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.ubb.mirko.concorde.model.User;
import com.ubb.mirko.concorde.repository.UserRepository;
import com.ubb.mirko.concorde.repository.UserRepositoryWithRoom;
import com.ubb.mirko.concorde.service.UserService;

/**
 * Created by mirko on 02/01/2018.
 */

public class UserController {
    private static final UserController ourInstance = newInstance();
    private UserService service;
    private User currentUser = null;

    public static UserController getInstance() {
        return ourInstance;
    }

    private static final UserController newInstance() {
        UserRepository repository = new UserRepositoryWithRoom();
        UserService service = new UserService(repository);
        return new UserController(service);
    }

    public UserController(UserService service) {
        this.service = service;
    }

    public User authenticate(String username, String password) {
        currentUser = service.authenticate(username, password);
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User authenticateWithGoogle(GoogleSignInAccount account) {
        currentUser = service.authenticateWithGoogle(account);
        return currentUser;
    }
}
