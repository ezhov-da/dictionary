package ru.ezhov.dictionary.informer.dao;

public interface WordDao {
     void load() throws Exception;

    String getRandomWord() throws Exception;
}
