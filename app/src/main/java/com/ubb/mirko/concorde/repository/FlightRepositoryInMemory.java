package com.ubb.mirko.concorde.repository;

import com.ubb.mirko.concorde.model.Flight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mirko on 16/12/2017.
 */

public class FlightRepositoryInMemory implements FlightRepository {
    private List<Flight> items_;
    private int nextId_;

    public FlightRepositoryInMemory() {
        items_ = new ArrayList<>();
        items_.add(new Flight("Cluj-Napoca", "Budapest", 30));
        items_.add(new Flight("Cluj-Napoca", "Bucharest", 15));
        items_.add(new Flight("Budapest", "Cluj-Napoca", 35));
        items_.add(new Flight("Budapest", "Bucharest", 33));
        items_.add(new Flight("Bucharest", "Cluj-Napoca", 18));
        nextId_ = 6;
    }

    @Override
    public void add(Flight newItem) {
        if (newItem.getId() == -1) {
            newItem.setId(nextId_);
            nextId_ += 1;
        }
        for (Flight item : items_) {
            if (item.getId() == newItem.getId()) {
                item.cloneFrom(newItem);
            }
        }
        items_.add(newItem);
    }

    @Override
    public void remove(Flight item) {
        remove(item.getId());
    }

    @Override
    public void remove(int id) {
        for (Flight item : items_) {
            if (item.getId() == id) {
                items_.remove(item);
                return;
            }
        }
    }

    @Override
    public List<Flight> get() {
        return items_;
    }

    @Override
    public Flight get(int id) {
        for (Flight item : items_) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
