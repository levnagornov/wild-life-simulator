package org.example.exception;

public class InvalidConfigFile extends RuntimeException {
    public InvalidConfigFile(String message) {
        super(message);
    }
}
