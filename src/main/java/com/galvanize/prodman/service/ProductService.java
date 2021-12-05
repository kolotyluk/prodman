package com.galvanize.prodman.service;

import com.galvanize.prodman.domain.Product;
import com.galvanize.prodman.model.ProductDTO;
import com.galvanize.prodman.model.ProductModel;
import com.galvanize.prodman.repository.ProductRepository;
import com.galvanize.prodman.rest.UnknownCurrencyException;
import org.springframework.stereotype.Service;


/**
 * <h1>Product Service</h1>
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CurrencyExchangeService fxService;

    public ProductService(final ProductRepository productRepository, final CurrencyExchangeService fxService) {
        this.productRepository = productRepository;
        this.fxService = fxService;
    }

    @Deprecated
    public Integer create(final ProductDTO productDTO) {
        final var product = new Product();
        mapToEntity(productDTO, product);
        return productRepository.save(product).getId();
    }

    public Integer create(final ProductModel productRecord) {
        return productRepository.save(productRecord.toEntity()).getId();
    }

    public ProductModel get(final Integer id, final String currency) throws UnknownCurrencyException {
        final var exchangeRate = (currency == null) ? 1.0 : fxService.getQuotes().get(currency);
        final var productEntity = productRepository.findById(id).get();
        productEntity.setViews(productEntity.getViews() + 1);
        productRepository.save(productEntity);
        productEntity.setPrice(productEntity.getPrice() * exchangeRate);
        return productEntity.toModel();
    }

    public ProductModel delete(final Integer id) {
        var productModel = productRepository.findById(id).get().toModel();
        productRepository.deleteById(id);
        return productModel;
    }

    @Deprecated
    private Product mapToEntity(final ProductDTO productDTO, final Product product) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setViews(0);
        product.setDeleted(false);
        return product;
    }

}
