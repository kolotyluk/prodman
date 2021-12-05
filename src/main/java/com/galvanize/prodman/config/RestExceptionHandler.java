package com.galvanize.prodman.config;

import com.galvanize.prodman.domain.ProductNotFoundException;
import com.galvanize.prodman.model.ErrorResponse;
import com.galvanize.prodman.model.FieldError;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.galvanize.prodman.rest.InvalidModelException;
import com.galvanize.prodman.rest.InvalidIdentityException;
import com.galvanize.prodman.rest.UnknownCurrencyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIdentity(final ProductNotFoundException cause) {
        var errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                cause.getClass().getSimpleName(),
                cause.getMessage(),
                Collections.emptyList());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIdentityException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIdentity(final InvalidIdentityException cause) {
        var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), cause.getClass().getSimpleName(), cause.getMessage(), Collections.emptyList());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnknownCurrencyException.class)
    public ResponseEntity<ErrorResponse> handleUnknownCurrency(final UnknownCurrencyException cause) {
        var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), cause.getClass().getSimpleName(), cause.getMessage(), Collections.emptyList());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidModelException.class)
    public ResponseEntity<ErrorResponse> handleInvalidModel(final InvalidModelException cause) {
        var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), cause.getClass().getSimpleName(), cause.getMessage(), cause.getFieldErrors());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(final ResponseStatusException cause) {
        final ErrorResponse errorResponse = new ErrorResponse(cause.getStatus().value(), cause.getClass().getSimpleName(), cause.getMessage(), Collections.emptyList());;
        return new ResponseEntity<>(errorResponse, cause.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
        final MethodArgumentNotValidException exception) {
            final BindingResult bindingResult = exception.getBindingResult();
            final List<FieldError> fieldErrors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> {
                        final FieldError fieldError = new FieldError(error.getField(), error.getCode());
                        return fieldError;
                    })
                    .collect(Collectors.toList());
            final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getClass().getSimpleName(), "", fieldErrors);

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(final Throwable cause) {
        cause.printStackTrace();
        final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), cause.getClass().getSimpleName(), "", Collections.emptyList());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
