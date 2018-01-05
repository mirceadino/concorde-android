package com.ubb.mirko.concorde.controller;

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
    private FlightRepository repository;

    public static FlightController getInstance() {
        return ourInstance;
    }

    public FlightController(FlightRepository repository) {
        this.repository = repository;
    }

    public List<Flight> getAllFlights() {
        return repository.get();
    }

    public void addFlight(Flight flight) {
        repository.add(flight);
    }

    public void removeFlight(Flight deletedFlight) {
        repository.remove(deletedFlight);
    }
}
