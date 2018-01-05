package com.ubb.mirko.concorde.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.ubb.mirko.concorde.dao.PriceConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public Flight(JSONObject object) throws JSONException {
        fromJSON(object);
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

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", getId());
        object.put("source", getSource());
        object.put("destination", getDestination());
        object.put("price", new JSONArray(getPrice()));
        return object;
    }

    public void fromJSON(JSONObject object) throws JSONException {
        id = (Integer) object.get("id");
        source = (String) object.get("source");
        destination = (String) object.get("destination");
        price = new ArrayList<>();
        JSONArray array = (JSONArray) object.get("price");
        for (int i = 0; i < array.length(); ++i) {
            price.add(array.getInt(i));
        }
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
