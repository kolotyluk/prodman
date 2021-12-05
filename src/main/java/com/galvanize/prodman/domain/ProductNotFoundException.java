package com.galvanize.prodman.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * <h1>Product Not Found in Repository</h1>
 */
public class ProductNotFoundException extends ResponseStatusException {
    public ProductNotFoundException(Integer identity, Throwable cause) {
        super(HttpStatus.NOT_FOUND, "No such Product with Identity = " + identity, cause);
    }
}
