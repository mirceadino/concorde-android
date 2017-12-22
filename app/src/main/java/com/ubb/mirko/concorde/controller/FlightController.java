package com.ubb.mirko.concorde.controller;

import com.ubb.mirko.concorde.db.AppDatabase;
import com.ubb.mirko.concorde.model.Flight;
import com.ubb.mirko.concorde.repository.FlightRepository;
import com.ubb.mirko.concorde.repository.FlightRepositoryWithRoom;

import java.util.List;
import java.util.Random;

/**
 * Created by mirko on 16/12/2017.
 */

public class FlightController {
    private static final FlightController ourInstance = new FlightController(new FlightRepositoryWithRoom());
    private FlightRepository repository_;

    public static FlightController getInstance() {
        return ourInstance;
    }

    public FlightController(FlightRepository repository) {
        repository_ = repository;
        // cleanDb();
        // populateDb();
    }

    public List<Flight> getAllFlights() {
        return repository_.get();
    }

    public void addFlight(Flight flight) {
        repository_.add(flight);
    }

    public void removeFlight(Flight deletedFlight) {
        repository_.remove(deletedFlight);
    }

    private void cleanDb() {
        List<Flight> flights = getAllFlights();
        for (Flight f : flights) {
            removeFlight(f);
        }
    }

    private int getRandom(Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private void populateDb() {
        Random random = new Random();
        Flight[] flights = new Flight[4];
        flights[0] = new Flight("Bucharest", "Budapest", getRandom(random, 10, 50));
        flights[1] = new Flight("Paris", "Budapest", getRandom(random, 10, 50));
        flights[2] = new Flight("Paris", "London", getRandom(random, 10, 50));
        flights[3] = new Flight("London", "Paris", getRandom(random, 10, 50));

        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < flights.length; ++j) {
                flights[j].setPrice(getRandom(random, 10, 50));
            }
        }

        for (int i = 0; i < flights.length; ++i) {
            addFlight(flights[i]);
        }
    }
}
