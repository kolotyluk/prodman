package com.galvanize.prodman.domain;

import com.galvanize.prodman.model.ProductModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Product {
    // TODO change this from class to record

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer id;     // TODO make this a UUID

    @Column(nullable = false, length = 50)
    private String name;

    @Column
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double price;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer views;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean deleted;

    @Version
    private Integer version;

    public ProductModel toModel() {
        return new ProductModel(id, name, description, price);
    }
}
