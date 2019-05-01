package cz.muni.fi.pb138.videolibrary.exception;

/**
 * Exception for invalid operations with entity.
 *
 */
public class IllegalEntityException extends RuntimeException {

    /**
     * No-arg constructor.
     */
    public IllegalEntityException() {
    }

    /**
     * Constructor with a message of an exception.
     *
     * @param message detail of the exception.
     */
    public IllegalEntityException(String message) {
        super(message);
    }
}
