package ru.ezhov.dictionary.informer.domain;

public class WordRepositoryException extends Exception {
    public WordRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public WordRepositoryException(String message) {
        super(message);
    }
}
