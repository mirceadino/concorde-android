package com.ubb.mirko.concorde.observer;

/**
 * Created by mirko on 05/01/2018.
 */

public interface Observable {
    void attach(Observer observer);

    void detach(Observer observer);

    void notifyObservers(Observer.ObserverStatus status, Object object);
}
