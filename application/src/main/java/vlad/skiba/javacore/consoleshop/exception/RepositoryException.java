package vlad.skiba.javacore.consoleshop.exception;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class RepositoryException extends ApplicationGenericException {

    public RepositoryException() {
        super();
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(Throwable cause) {
        super(cause);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

}