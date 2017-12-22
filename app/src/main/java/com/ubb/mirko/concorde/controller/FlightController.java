package com.ubb.mirko.concorde.controller;

import com.ubb.mirko.concorde.model.Flight;
import com.ubb.mirko.concorde.repository.FlightRepository;
import com.ubb.mirko.concorde.repository.FlightRepositoryWithRoom;

import java.util.List;

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
}

