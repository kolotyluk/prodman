package com.galvanize.prodman.model;

import com.galvanize.prodman.rest.UnknownCurrencyException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;


@Getter
@Setter
public class CurrencyExchangeResponse {

  private Map<String, Double> quotes;

  public Double get(String currency) throws UnknownCurrencyException {
    final var exchangeRate = quotes.get( "USD" + currency);
    if (exchangeRate == null) throw new UnknownCurrencyException(currency, this);
    return exchangeRate;
  }

  public List<String> getCurrencies() {
    return quotes.keySet().stream().map(key -> key.substring(3)).toList();
  }

  @Override
  public String toString() {
    return quotes.toString();
  }
}
