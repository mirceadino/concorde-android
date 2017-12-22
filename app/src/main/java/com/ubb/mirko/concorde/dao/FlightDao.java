package com.ubb.mirko.concorde.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ubb.mirko.concorde.model.Flight;

import java.util.List;

/**
 * Created by mirko on 22/12/2017.
 */

@Dao
public interface FlightDao {
    @Query("SELECT * FROM flight")
    List<Flight> getAllFlights();

    @Query("SELECT * FROM flight WHERE id IS :id")
    Flight getFlightWithId(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Flight flight);

    @Delete
    void delete(Flight flight);
}
