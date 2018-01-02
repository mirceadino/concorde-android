package com.ubb.mirko.concorde.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.ubb.mirko.concorde.App;
import com.ubb.mirko.concorde.dao.FlightDao;
import com.ubb.mirko.concorde.dao.UserDao;
import com.ubb.mirko.concorde.model.Flight;
import com.ubb.mirko.concorde.model.User;

/**
 * Created by mirko on 22/12/2017.
 */

@Database(entities = {Flight.class, User.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FlightDao flightDao();

    public abstract UserDao userDao();

    private static final AppDatabase ourInstance = Room.databaseBuilder(App.getContext(),
            AppDatabase.class, "concorde-db").fallbackToDestructiveMigration().allowMainThreadQueries().build();

    public static AppDatabase getInstance() {
        return ourInstance;
    }
}
