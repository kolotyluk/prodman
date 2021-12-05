package com.galvanize.prodman.rest;

import com.galvanize.prodman.model.CurrencyExchangeResponse;

public class UnknownCurrencyException extends Exception {
    public UnknownCurrencyException(final String currency, final CurrencyExchangeResponse fxResponse) {
        super("Unknown currency = '%s': must be one of %s".formatted(currency, fxResponse.getCurrencies()));
    }
}
