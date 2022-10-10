package ru.javawebinar.topjava.exception;

public class NotFoundStorageException extends RuntimeException {
    public NotFoundStorageException(String message) {
        super(message);
    }
}
