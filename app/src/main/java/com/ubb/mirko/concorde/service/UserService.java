package com.ubb.mirko.concorde.service;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.ubb.mirko.concorde.model.User;
import com.ubb.mirko.concorde.repository.UserRepository;

import java.util.List;

/**
 * Created by mirko on 05/01/2018.
 */

public class UserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User authenticate(String username, String password) {
        User user = repository.get(username);
        if (user != null && !user.getPassword().equals(password)) {
            user = null;
        }
        return user;
    }

    public User authenticateWithGoogle(GoogleSignInAccount account) {
        User user = repository.get(account.getId());
        if (user == null) {
            String username = account.getId();
            String password = account.getId();
            user = new User(username, password, false);
            repository.add(user);
        }
        return user;
    }
}
