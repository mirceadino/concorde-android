package com.ubb.mirko.concorde.repository;

import com.ubb.mirko.concorde.db.AppDatabase;
import com.ubb.mirko.concorde.model.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mirko on 22/12/2017.
 */

public class FlightRepositoryWithRoom implements FlightRepository {
    private AppDatabase db = AppDatabase.getInstance();

    public FlightRepositoryWithRoom() {
        // cleanAndPopulate();
    }

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

    @Override
    public void set(List<Flight> list) {
        for (Flight flight : get()) {
            remove(flight);
        }

        for(Flight flight: list){
            add(flight);
        }
    }

    private int getRandom(Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private void cleanAndPopulate() {
        Random random = new Random();
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight("Bucharest", "Budapest", getRandom(random, 10, 50)));
        flights.add(new Flight("Paris", "Budapest", getRandom(random, 10, 50)));
        flights.add(new Flight("Paris", "London", getRandom(random, 10, 50)));
        flights.add(new Flight("London", "Paris", getRandom(random, 10, 50)));

        for (int i = 0; i < 4; ++i) {
            for (Flight flight : flights) {
                flight.setPrice(getRandom(random, 10, 50));
            }
        }

        set(flights);
    }
}
