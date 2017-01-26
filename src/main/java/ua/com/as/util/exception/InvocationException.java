package ua.com.as.util.exception;

/**
 * <p>Thrown to indicate that there are some error in methods which are using Reflection.
 */
public class InvocationException extends RuntimeException {

    /** Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public InvocationException(String message) {
        super(message);
    }

    /**
     * <p>Constructs a new invocation exception with the specified detail message and
     * cause.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method). Null value permitted.
     */
    public InvocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
