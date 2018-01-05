package com.ubb.mirko.concorde.repository;

import com.ubb.mirko.concorde.model.Flight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mirko on 16/12/2017.
 */

public class FlightRepositoryInMemory implements FlightRepository {
    private List<Flight> items;
    private int nextId;

    public FlightRepositoryInMemory() {
        items = new ArrayList<>();
        items.add(new Flight("Cluj-Napoca", "Budapest", 30));
        items.add(new Flight("Cluj-Napoca", "Bucharest", 15));
        items.add(new Flight("Budapest", "Cluj-Napoca", 35));
        items.add(new Flight("Budapest", "Bucharest", 33));
        items.add(new Flight("Bucharest", "Cluj-Napoca", 18));
        nextId = 6;
    }

    @Override
    public void add(Flight newItem) {
        if (newItem.getId() == -1) {
            newItem.setId(nextId);
            nextId += 1;
        }
        for (Flight item : items) {
            if (item.getId() == newItem.getId()) {
                item.cloneFrom(newItem);
            }
        }
        items.add(newItem);
    }

    @Override
    public void remove(Flight item) {
        remove(item.getId());
    }

    @Override
    public void remove(int id) {
        for (Flight item : items) {
            if (item.getId() == id) {
                items.remove(item);
                return;
            }
        }
    }

    @Override
    public List<Flight> get() {
        return items;
    }

    @Override
    public Flight get(int id) {
        for (Flight item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
