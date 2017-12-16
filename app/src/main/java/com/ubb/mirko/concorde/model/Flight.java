package com.ubb.mirko.concorde.model;

import java.io.Serializable;

/**
 * Created by mirko on 12/11/2017.
 */

public class Flight implements Serializable {
    private int id;
    private String source;
    private String destination;
    private int price;

    public Flight(int id, String source, String destination, int price) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.price = price;
    }

    public void cloneFrom(Flight that) {
        this.id = that.id;
        this.source = that.source;
        this.destination = that.destination;
        this.price = that.price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", price=" + price +
                '}';
    }
}
