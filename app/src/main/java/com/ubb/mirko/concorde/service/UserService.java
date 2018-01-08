package com.ubb.mirko.concorde.service;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ubb.mirko.concorde.model.User;
import com.ubb.mirko.concorde.observer.Observable;
import com.ubb.mirko.concorde.observer.Observer;
import com.ubb.mirko.concorde.observer.Observer.ObserverStatus;
import com.ubb.mirko.concorde.repository.UserRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mirko on 05/01/2018.
 */

public class UserService implements Observable {
    private static final String HOST = "10.152.5.194";
    private static final Integer PORT = 5000;
    private static final String URL = "http://" + HOST + ":" + PORT;
    private static final AsyncHttpClient httpClient = new AsyncHttpClient();
    private UserRepository repository;
    private List<Observer> observers = new ArrayList<>();
    private User currentUser = null;

    public UserService(UserRepository repository) {
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

    public void authenticate(String username, String password) {
        currentUser = null;
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        httpClient.get(URL + "/users", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    currentUser = new User(object);
                    notifyObservers(ObserverStatus.OK, "Connected.");

                } catch (JSONException e) {
                    notifyObservers(ObserverStatus.FAIL, "Wrong username and password.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                notifyObservers(ObserverStatus.FAIL, "Offline mode. Can't connect to server.");
            }

            @Override
            public void onRetry(int retryNo) {
                notifyObservers(ObserverStatus.FAIL, "Trying to connect...");
            }
        });
    }

    public void logout() {
        currentUser = null;
        notifyObservers(ObserverStatus.OK, "Disconnected.");
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void authenticateWithGoogle(GoogleSignInAccount account) {
        currentUser = null;
        String username = account.getId();
        String password = account.getId();
        RequestParams params = new RequestParams();
        User user = new User(username, password, false);
        JSONObject object = null;
        try {
            object = user.toJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("user", object);
        httpClient.post(URL + "/users", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    currentUser = new User(object);
                    notifyObservers(ObserverStatus.OK, "Connected.");

                } catch (JSONException e) {
                    notifyObservers(ObserverStatus.FAIL, "Something went wrong.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                notifyObservers(ObserverStatus.FAIL, "Offline mode. Can't connect to server.");
            }

            @Override
            public void onRetry(int retryNo) {
                notifyObservers(ObserverStatus.FAIL, "Trying to connect...");
            }
        });
    }
}
