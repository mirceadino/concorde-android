package com.ubb.mirko.concorde.repository;

import com.ubb.mirko.concorde.db.AppDatabase;
import com.ubb.mirko.concorde.model.Flight;

import java.util.List;

/**
 * Created by mirko on 22/12/2017.
 */

public class FlightRepositoryWithRoom implements FlightRepository {
    private AppDatabase db = AppDatabase.getInstance();

    @Override
    public void add(Flight item) {
        db.flightDao().insert(item);
    }

    @Override
    public void remove(Flight item) {
        db.flightDao().delete(item);
    }

    @Override
    public void remove(int id) {
        db.flightDao().delete(get(id));
    }

    @Override
    public List<Flight> get() {
        return db.flightDao().getAllFlights();
    }

    @Override
    public Flight get(int id) {
        return db.flightDao().getFlightWithId(id);
    }
}
