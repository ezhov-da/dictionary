package ru.ezhov.dictionary.pid;

public class PidRepositoryException extends Exception {

    public PidRepositoryException() {
    }

    public PidRepositoryException(String message) {
        super(message);
    }

    public PidRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public PidRepositoryException(Throwable cause) {
        super(cause);
    }

    public PidRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
