package vlad.skiba.javacore.consoleshop.exception;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class ConnectionException extends ApplicationGenericException {

    public ConnectionException() {
        super();
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(Throwable cause) {
        super(cause);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}