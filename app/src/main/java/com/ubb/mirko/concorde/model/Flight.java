package com.ubb.mirko.concorde.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.ubb.mirko.concorde.dao.PriceConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mirko on 22/12/2017.
 */

@Entity
public class Flight implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "source")
    private String source;

    @ColumnInfo(name = "destination")
    private String destination;

    @TypeConverters(PriceConverter.class)
    private List<Integer> price;

    public Flight(String source, String destination, int price) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.price = new ArrayList<>();
        this.price.add(price);
    }

    public Flight(String source, String destination, List<Integer> price) {
        this.source = source;
        this.destination = destination;
        this.price = new ArrayList<>();
        this.price.addAll(price);
    }

    public void cloneFrom(Flight that) {
        this.id = that.id;
        this.source = that.source;
        this.destination = that.destination;
        this.price = new ArrayList<>();
        this.price.addAll(that.price);
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

    public int getLastPrice() {
        return price.get(price.size() - 1);
    }

    public List<Integer> getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price.add(price);
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
