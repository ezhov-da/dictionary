package ru.ezhov.dictionary.pid;

public class PidException extends Exception {
    public PidException() {
    }

    public PidException(String message) {
        super(message);
    }

    public PidException(String message, Throwable cause) {
        super(message, cause);
    }

    public PidException(Throwable cause) {
        super(cause);
    }

    public PidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
