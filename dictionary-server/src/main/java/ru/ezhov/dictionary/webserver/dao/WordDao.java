package ru.ezhov.dictionary.webserver.dao;

import java.util.List;

public interface WordDao {

    int count();

    boolean isChange() throws Exception;

    void load() throws Exception;

    void reload() throws Exception;

    List<String> getAll();

    String getWord(int i);
}
