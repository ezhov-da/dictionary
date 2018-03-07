package ru.ezhov.dictionary.webserver.dao;

public interface WordDao {
     void load() throws Exception;

    String getRandomWord() throws Exception;
}
