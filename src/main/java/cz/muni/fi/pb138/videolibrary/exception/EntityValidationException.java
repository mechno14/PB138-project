package cz.muni.fi.pb138.videolibrary.exception;

/**
 * Exception for invalid attributes of entity.
 *
 */
public class EntityValidationException extends RuntimeException {

    /**
     * No-arg constructor.
     */
    public EntityValidationException() {
    }

    /**
     * Constructor with a message of an exception.
     *
     * @param message detail of the exception.
     */
    public EntityValidationException(String message) {
        super(message);
    }
}
