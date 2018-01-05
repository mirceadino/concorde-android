package com.ubb.mirko.concorde.service;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.ubb.mirko.concorde.model.Flight;
import com.ubb.mirko.concorde.observer.Observable;
import com.ubb.mirko.concorde.observer.Observer;
import com.ubb.mirko.concorde.observer.Observer.ObserverStatus;
import com.ubb.mirko.concorde.repository.FlightRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mirko on 05/01/2018.
 */

public class FlightService implements Observable {
    private static final String HOST = "10.152.5.194";
    private static final Integer PORT = 5000;
    private static final String URL = "http://" + HOST + ":" + PORT;
    private static final AsyncHttpClient httpClient = new AsyncHttpClient();
    private FlightRepository repository;
    private List<Observer> observers = new ArrayList<>();

    public FlightService(FlightRepository repository) {
        this.repository = repository;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(ObserverStatus status, Object object) {
        for (Observer observer : observers) {
            observer.update(status, object);
        }
    }

    public void fetchFlights() {
        httpClient.get(URL + "/flights", new AsyncHttpResponseHandlerForFlightService(this) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray object = new JSONArray(new String(responseBody));
                    List<Flight> flights = new ArrayList<>();
                    for (int i = 0; i < object.length(); ++i) {
                        flights.add(new Flight(object.getJSONObject(i)));
                    }
                    repository.set(flights);
                    notifyObservers(ObserverStatus.OK, repository.get());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<Flight> getAllFlights() {
        fetchFlights();
        return repository.get();
    }

    public void addFlight(Flight flight) {
        try {
            JSONObject object = flight.toJSON();
            System.out.println(object);
            RequestParams params = new RequestParams();
            params.put("flight", object);
            httpClient.post(URL + "/flights", params, new AsyncHttpResponseHandlerForFlightService(this));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removeFlight(Flight deletedFlight) {
        try {
            JSONObject object = deletedFlight.toJSON();
            System.out.println(object);
            RequestParams params = new RequestParams();
            params.put("flight", object);
            httpClient.delete(URL + "/flights", params, new AsyncHttpResponseHandlerForFlightService(this));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
