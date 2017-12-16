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
        items_.add(new Flight(1, "Cluj-Napoca", "Budapest", 30));
        items_.add(new Flight(2, "Cluj-Napoca", "Bucharest", 15));
        items_.add(new Flight(3, "Budapest", "Cluj-Napoca", 35));
        items_.add(new Flight(4, "Budapest", "Bucharest", 33));
        items_.add(new Flight(5, "Bucharest", "Cluj-Napoca", 18));
        nextId_ = 6;
    }

    @Override
    public Flight add(Flight newItem) {
        if (newItem.getId() == -1) {
            newItem.setId(nextId_);
            nextId_ += 1;
        }
        for (Flight item : items_) {
            if (item.getId() == newItem.getId()) {
                System.out.println(item);
                System.out.println(newItem);
                item.cloneFrom(newItem);
                return item;
            }
        }
        items_.add(newItem);
        return newItem;
    }

    @Override
    public Flight remove(Flight item) {
        return remove(item.getId());
    }

    @Override
    public Flight remove(int id) {
        Flight removedItem = null;
        for (Flight item : items_) {
            if (item.getId() == id) {
                removedItem = item;
                items_.remove(item);
                break;
            }
        }
        return removedItem;
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
