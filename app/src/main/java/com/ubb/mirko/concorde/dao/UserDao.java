package com.ubb.mirko.concorde.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ubb.mirko.concorde.model.User;

import java.util.List;

/**
 * Created by mirko on 22/12/2017.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE id IS :id")
    User getUserWithId(int id);

    @Query("SELECT * FROM user WHERE username IS :username")
    User getUserWithUsername(String username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);
}
