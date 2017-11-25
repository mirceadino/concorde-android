package com.ubb.mirko.concorde.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mirko on 25/11/2017.
 */

public class FlightInMemoryDataset {
    private static final FlightInMemoryDataset ourInstance = new FlightInMemoryDataset();
    private List<Flight> dataset_;

    public static FlightInMemoryDataset getInstance() {
        return ourInstance;
    }

    private FlightInMemoryDataset() {
        dataset_ = new ArrayList<>();
        dataset_.add(new Flight(1, "Cluj-Napoca", "Budapest", 30));
        dataset_.add(new Flight(2, "Cluj-Napoca", "Bucharest", 15));
        dataset_.add(new Flight(3, "Budapest", "Cluj-Napoca", 35));
        dataset_.add(new Flight(4, "Budapest", "Bucharest", 33));
        dataset_.add(new Flight(5, "Bucharest", "Cluj-Napoca", 18));
    }

    public List<Flight> getDataset() {
        return dataset_;
    }

    public void addFlight(Flight newFlight) {
        for (Flight flight : dataset_) {
            if (flight.getId() == newFlight.getId()) {
                flight.setSource(newFlight.getSource());
                flight.setDestination(newFlight.getDestination());
                flight.setPrice(newFlight.getPrice());
                return;
            }
        }
        dataset_.add(newFlight);
    }
}
