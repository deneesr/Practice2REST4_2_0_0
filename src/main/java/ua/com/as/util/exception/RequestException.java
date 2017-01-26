package ua.com.as.util.exception;

/**
 *
 */
public class RequestException extends RuntimeException {

    /**
     * Constructs a new request exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public RequestException(String message) {
        super(message);
    }

}
