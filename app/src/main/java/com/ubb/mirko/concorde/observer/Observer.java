package com.ubb.mirko.concorde.observer;

/**
 * Created by mirko on 05/01/2018.
 */

public interface Observer {
    enum ObserverStatus {
        OK, FAIL
    }

    void update(ObserverStatus status, Object object);
}
