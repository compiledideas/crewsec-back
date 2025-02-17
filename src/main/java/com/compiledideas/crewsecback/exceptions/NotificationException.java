package com.compiledideas.crewsecback.exceptions;

public class NotificationException extends RuntimeException {
    public NotificationException(String message) {
        super(message);
    }

    public NotificationException() {
        super("Notification error");
    }
}
