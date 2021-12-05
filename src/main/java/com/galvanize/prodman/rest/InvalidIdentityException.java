package com.galvanize.prodman.rest;

/**
 * <h1>REST Identity Not Valid</h1>
 */
public class InvalidIdentityException extends Exception {
    InvalidIdentityException(String message, Throwable cause) {
        super(message, cause);
    }
}
