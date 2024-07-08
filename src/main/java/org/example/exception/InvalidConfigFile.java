package org.example.exception;

/**
 * The {@code InvalidConfigFile} class is a custom exception that indicates an issue with reading or parsing a configuration file.
 * It extends the {@code RuntimeException} class.
 */
public class InvalidConfigFile extends RuntimeException {

    /**
     * Constructs a new InvalidConfigFile exception with the specified detail message.
     *
     * @param message the detail message that describes the reason for the exception
     */
    public InvalidConfigFile(String message) {
        super(message);
    }
}
