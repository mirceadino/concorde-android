package com.ubb.mirko.concorde.repository;

import java.util.List;

/**
 * Created by mirko on 16/12/2017.
 */

public interface Repository<T> {
    T add(T item);

    T remove(T item);

    T remove(int id);

    List<T> get();

    T get(int id);
}
