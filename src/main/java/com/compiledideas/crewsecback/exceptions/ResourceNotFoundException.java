package com.compiledideas.crewsecback.exceptions;

public class ResourceNotFoundException
extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
