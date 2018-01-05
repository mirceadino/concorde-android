package com.ubb.mirko.concorde.controller;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.ubb.mirko.concorde.model.User;
import com.ubb.mirko.concorde.observer.Observer;
import com.ubb.mirko.concorde.repository.UserRepository;
import com.ubb.mirko.concorde.repository.UserRepositoryWithRoom;
import com.ubb.mirko.concorde.service.UserService;

/**
 * Created by mirko on 02/01/2018.
 */

public class UserController {
    private static final UserController ourInstance = newInstance();
    private UserService service;

    public static UserController getInstance() {
        return ourInstance;
    }

    private static UserController newInstance() {
        UserRepository repository = new UserRepositoryWithRoom();
        UserService service = new UserService(repository);
        return new UserController(service);
    }

    public UserController(UserService service) {
        this.service = service;
    }

    public void authenticate(String username, String password) {
        service.authenticate(username, password);
    }

    public void logout() {
        service.logout();
    }

    public User getCurrentUser() {
        return service.getCurrentUser();
    }

    public void authenticateWithGoogle(GoogleSignInAccount account) {
        service.authenticateWithGoogle(account);
    }

    public void subscribe(Observer observer){
        service.attach(observer);
    }
}
