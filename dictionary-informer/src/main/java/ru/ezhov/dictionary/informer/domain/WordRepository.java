package ru.ezhov.dictionary.informer.domain;

import java.util.List;

public interface WordRepository {
    int count();

    boolean isChange() throws WordRepositoryException;

    void load() throws WordRepositoryException;

    void reload() throws WordRepositoryException;

    List<String> all();

    String getWord(int i);
}
