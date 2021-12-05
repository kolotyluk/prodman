package com.galvanize.prodman.rest;

import com.galvanize.prodman.model.FieldError;

import java.util.List;

/**
 * <h1>Model JSON Not Valid</h1>
 */
public class InvalidModelException extends Exception {
    final private List<FieldError> fieldErrors;

    public InvalidModelException(String modelName, List<FieldError> fieldErrors) {
        super("Invalid JSON payload for " + modelName);
        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() { return fieldErrors; }
}
