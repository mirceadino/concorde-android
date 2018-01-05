package com.ubb.mirko.concorde.controller;

import com.ubb.mirko.concorde.model.Flight;
import com.ubb.mirko.concorde.repository.FlightRepository;
import com.ubb.mirko.concorde.repository.FlightRepositoryWithRoom;
import com.ubb.mirko.concorde.service.FlightService;

import java.util.List;

/**
 * Created by mirko on 16/12/2017.
 */

public class FlightController {
    private static final FlightController ourInstance = newInstance();
    private FlightService service;

    private static FlightController newInstance() {
        FlightRepository repository = new FlightRepositoryWithRoom();
        FlightService service = new FlightService(repository);
        return new FlightController(service);
    }

    public static FlightController getInstance() {
        return ourInstance;
    }

    public FlightController(FlightService service) {
        this.service = service;
    }

    public List<Flight> getAllFlights() {
        return service.getAllFlights();
    }

    public void addFlight(Flight flight) {
        service.addFlight(flight);
    }

    public void removeFlight(Flight deletedFlight) {
        service.removeFlight(deletedFlight);
    }
}
