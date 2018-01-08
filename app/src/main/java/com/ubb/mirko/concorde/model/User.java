package com.ubb.mirko.concorde.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

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

    @ColumnInfo(name = "isIbis")
    private boolean isIbis;

    public User(String username, String password, boolean isIbis) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isIbis = isIbis;
    }

    public User(JSONObject object) throws JSONException {
        fromJSON(object);
    }

    public void cloneFrom(User that) {
        this.id = that.id;
        this.username = that.username;
        this.password = that.password;
        this.isIbis = that.isIbis;
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

    public boolean isIbis() {
        return isIbis;
    }

    public void setIbis(boolean isIbis) {
        this.isIbis = isIbis;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", getId());
        object.put("username", getUsername());
        object.put("password", getPassword());
        object.put("isIbis", isIbis());
        return object;
    }

    public void fromJSON(JSONObject object) throws JSONException {
        id = (Integer) object.get("id");
        username = (String) object.get("username");
        password = (String) object.get("password");
        isIbis = (Boolean) object.get("isIbis");
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isIbis=" + isIbis +
                '}';
    }
}
