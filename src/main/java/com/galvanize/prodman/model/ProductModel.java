package com.galvanize.prodman.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.galvanize.prodman.domain.Product;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public record ProductModel(
        Integer id,
        @NotNull
        @Size(max = 50)
        String name,
        @Size(max = 255)
        String description,
        @NotNull
        @Digits(integer = 10, fraction = 2)
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        // @Schema(type = "string", example = "1.00")
        Double price
) {
    public Product toEntity() {
        var product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setViews(0);
        product.setDeleted(false);
        return product;
    }
}
