package vlad.skiba.javacore.consoleshop.exception;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class OrderException extends ApplicationGenericException {

    public OrderException() {
        super();
    }

    public OrderException(String message) {
        super(message);
    }

    public OrderException(Throwable cause) {
        super(cause);
    }

    public OrderException(String message, Throwable cause) {
        super(message, cause);
    }

}