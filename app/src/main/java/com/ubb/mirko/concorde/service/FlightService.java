package com.ubb.mirko.concorde.service;

import com.ubb.mirko.concorde.model.Flight;
import com.ubb.mirko.concorde.repository.FlightRepository;

import java.util.List;

/**
 * Created by mirko on 05/01/2018.
 */

public class FlightService {
    private FlightRepository repository;

    public FlightService(FlightRepository repository) {
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
