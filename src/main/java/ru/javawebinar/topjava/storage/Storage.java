package ru.javawebinar.topjava.storage;

import java.util.List;
import java.util.Optional;

public interface Storage<O, K> {

    void deleteAll();

    void deleteById(K id);

    Optional<O> findById(K id);

    List<O> findAll();

    void save(O entity);
}