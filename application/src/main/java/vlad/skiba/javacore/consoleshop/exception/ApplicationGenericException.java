package vlad.skiba.javacore.consoleshop.exception;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class ApplicationGenericException extends RuntimeException {

    public ApplicationGenericException() {
        super();
    }

    public ApplicationGenericException(String message) {
        super(message);
    }

    public ApplicationGenericException(Throwable cause) {
        super(cause);
    }

    public ApplicationGenericException(String message, Throwable cause) {
        super(message, cause);
    }

}