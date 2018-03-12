package ru.ezhov.dictionary.webserver.dao;

public interface WordGeneratorDao {

    String getRandomWord() throws Exception;
}
