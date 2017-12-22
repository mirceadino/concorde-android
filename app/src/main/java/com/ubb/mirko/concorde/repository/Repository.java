package com.ubb.mirko.concorde.repository;

import java.util.List;

/**
 * Created by mirko on 16/12/2017.
 */

public interface Repository<T> {
    void add(T item);

    void remove(T item);

    void remove(int id);

    List<T> get();

    T get(int id);
}
