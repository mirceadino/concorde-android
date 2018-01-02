package com.ubb.mirko.concorde.repository;

import com.ubb.mirko.concorde.db.AppDatabase;
import com.ubb.mirko.concorde.model.User;

import java.util.List;

/**
 * Created by mirko on 22/12/2017.
 */

public class UserRepositoryWithRoom implements UserRepository {
    private AppDatabase db = AppDatabase.getInstance();

    @Override
    public void add(User item) {
        db.userDao().insert(item);
    }

    @Override
    public void remove(User item) {
        db.userDao().delete(item);
    }

    @Override
    public void remove(int id) {
        db.userDao().delete(get(id));
    }

    @Override
    public List<User> get() {
        return db.userDao().getAllUsers();
    }

    @Override
    public User get(int id) {
        return db.userDao().getUserWithId(id);
    }

    @Override
    public User get(String username) {return db.userDao().getUserWithUsername(username);}
}
