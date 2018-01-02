package com.ubb.mirko.concorde.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by mirko on 02/01/2018.
 */

@Entity
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "is_ibis")
    private boolean is_ibis;

    public User(String username, String password, boolean is_ibis) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.is_ibis = is_ibis;
    }

    public void cloneFrom(User that) {
        this.id = that.id;
        this.username = that.username;
        this.password = that.password;
        this.is_ibis = that.is_ibis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean is_ibis() {
        return is_ibis;
    }

    public void set_ibis(boolean is_ibis) {
        this.is_ibis = is_ibis;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", is_ibis=" + is_ibis +
                '}';
    }
}
