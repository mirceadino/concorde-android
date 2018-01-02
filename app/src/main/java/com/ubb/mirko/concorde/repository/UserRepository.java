package com.ubb.mirko.concorde.repository;

import com.ubb.mirko.concorde.model.User;

/**
 * Created by mirko on 16/12/2017.
 */

public interface UserRepository extends Repository<User> {
    User get(String username);
}
