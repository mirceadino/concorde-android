package com.ubb.mirko.concorde.service;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.ubb.mirko.concorde.observer.Observer.ObserverStatus;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mirko on 05/01/2018.
 */

public class AsyncHttpResponseHandlerForFlightService extends AsyncHttpResponseHandler {
    private FlightService service;

    public AsyncHttpResponseHandlerForFlightService(FlightService service) {
        this.service = service;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        service.fetchFlights();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        service.notifyObservers(ObserverStatus.FAIL, "Offline mode. Can't connect to server.");
    }

    @Override
    public void onRetry(int retryNo) {
        service.notifyObservers(ObserverStatus.FAIL, "Trying to connect...");
    }
}
