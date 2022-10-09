package ru.javawebinar.topjava.storage;

import java.util.List;
import java.util.Optional;

public interface Storage<O, K> {

    void deleteById(K id);

    Optional<O> findById(K id);

    List<O> findAll();

    O save(O entity);
}