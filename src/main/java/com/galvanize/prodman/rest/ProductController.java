package com.galvanize.prodman.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.prodman.domain.ProductNotFoundException;
import com.galvanize.prodman.model.FieldError;
import com.galvanize.prodman.model.ProductModel;
import com.galvanize.prodman.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * <h1>REST Controller for Product API</h1>
 * Testing:
 * <pre>
 * curl -i -d \
 *     '{"name":"widget","description":"classic widget","price":"100.00"}'
 *     -H "Content-Type: application/json" \
 *     -X POST http://localhost:8080/api/product
 * curl -i http://localhost:8080/api/product/1
 * curl -i http://localhost:8080/api/product/1?currency=CAD
 * curl -i http://localhost:8080/api/product/1?currency=CDN
 * curl -i -X DELETE http://localhost:8080/api/product/1
 * curl -i http://localhost:8080/api/product/1
 * </pre>
 * @see <a href="https://newbedev.com/objectmapper-best-practice-for-thread-safety-and-performance">ObjectMapper - Best practice for thread-safety and performance</a>
 */
@RestController
@RequestMapping(value = "/api/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) { this.productService = productService; }

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    private static <T> T toObject(final Class<T> type, final String json) throws JsonProcessingException {
        return jsonMapper.readerFor(type).readValue(json);
    }


    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @DeleteMapping("/product/{identity}")
    public ResponseEntity delete(@PathVariable String identity) throws InvalidIdentityException, JsonProcessingException {
        var id = 0;
        try {
            id = Integer.parseInt(identity);
            return new ResponseEntity(jsonMapper.writeValueAsString(productService.delete(id)), HttpStatus.OK);
        }
        catch (NoSuchElementException cause) {
            throw new ProductNotFoundException(id, cause);
        }
        catch (NumberFormatException cause) {
            throw new InvalidIdentityException("Object Identity '%s' is not an integer".formatted(identity), cause);
        }
        catch (JsonProcessingException cause) {
            // TODO does this ever get caught?
            cause.printStackTrace();
            throw cause;
        }
    }

    @GetMapping("/product/{identity}")
    public ResponseEntity get(@PathVariable String identity, @PathParam("currency") String currency)
            throws InvalidIdentityException, JsonProcessingException, UnknownCurrencyException
    {
        var id = 0;
        try {
            id = Integer.parseInt(identity);
            return new ResponseEntity(jsonMapper.writeValueAsString(productService.get(id, currency)), HttpStatus.OK);
        }
        catch (NoSuchElementException cause) {
            throw new ProductNotFoundException(id, cause);
        }
        catch (NumberFormatException e) {
            throw new InvalidIdentityException("Object Identity '%s' is not an integer".formatted(identity), e);
        }
        catch (JsonProcessingException e) {
            // TODO does this ever get caught?
            e.printStackTrace();
            throw e;
        }
    }

    @PostMapping("/product")    // TODO https://www.baeldung.com/javax-validation
    public ResponseEntity post(@RequestBody String json) throws JsonProcessingException, InvalidModelException {
        try {
            final var productModel = toObject(ProductModel.class, json);
            Integer id = productService.create(valid(productModel));
            return new ResponseEntity(String.format("{ \"identity\" = \"%d\" }", id), HttpStatus.OK);
        } catch (JsonProcessingException cause) {
            // TODO handle this better
            cause.printStackTrace();
            return new ResponseEntity("{\"error\" : \"json\"}", HttpStatus.BAD_REQUEST);
        }
    }

    // We do this in the Controller because it's a Controller concern, not a Model concern,
    // but I could be persuaded otherwise...
    ProductModel valid(ProductModel productModel) throws InvalidModelException {

        final var violations = validator.validate(productModel);

        if (violations.isEmpty()) return productModel;

        final var fieldErrors = violations.stream()
                .map(violation ->
                    new FieldError(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();

        throw new InvalidModelException("Product", fieldErrors);
    }

}
