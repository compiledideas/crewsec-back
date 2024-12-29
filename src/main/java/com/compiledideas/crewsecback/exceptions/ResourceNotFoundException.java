package com.compiledideas.crewsecback.exceptions;

public class ResourceNotFoundException
extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String recourse, String field, Object value) {
        super(String.format("Can't find '%s' with '%s': '%s'", recourse, field, value));
    }
}
