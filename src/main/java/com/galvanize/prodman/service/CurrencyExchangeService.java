package com.galvanize.prodman.service;

import com.galvanize.prodman.model.CurrencyExchangeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <h1>Currency Exchange Service</h1>
 *
 * @see <a href="https://en.wikipedia.org/wiki/ISO_4217">ISO 4217</a>
 */
@Service
public class CurrencyExchangeService {
  // I renamed this from FxService to ExchangeService because I don't like acronyms

  private static final String SUPPORTED_CURRENCIES = "USD,CAD,EUR,GBP";

  @Value("${fx.api.url}")
  private String fxApiUrl;

  @Value("${fx.api.key}")
  private String fxApiKey;

  private final RestTemplate restTemplate;

  public CurrencyExchangeService(final RestTemplateBuilder restTemplateBuilder) { this.restTemplate = restTemplateBuilder.build(); }

  public CurrencyExchangeResponse getQuotes() {
    String endPoint = String.format("%s?access_key=%s&currencies=%s&format=1", fxApiUrl, fxApiKey, SUPPORTED_CURRENCIES);
    return restTemplate.getForObject(endPoint, CurrencyExchangeResponse.class);
  }
}