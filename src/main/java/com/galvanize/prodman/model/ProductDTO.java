package com.galvanize.prodman.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * <h1>Product Data Transfer Object</h1>
 * <p>
 *     This was deprecated because Java record types are the new best practice, in particular for
 *     <a href="https://en.wikipedia.org/wiki/Data_transfer_object">DTOs</a>, and for other good reasons.
 * </p>
 * <p>
 *     This has been replaced with {@link ProductModel}, and renamed it because I don't like acronyms. I don't know
 *     if the name <em>ProductModel</em> is the best choice, and I feel that the
 *     {@link com.galvanize.prodman.domain.Product} entity should really be named <em>ProductEntity</em>, but in
 *     Computing Science, naming things is one of the two hardest things.
 * </p>
 * <p>
 *     While Lombok has served us well, I am not sad to see it go, as it introduces complexity into the project, and
 *     can often be a source of errors during builds. Using records, we don't need lombok, as now the Java compiler
 *     takes care of getters, setters, and other things directly, and works well, better, with most serialization
 *     and deserialization.
 * </p>
 * @see ProductModel
 * @see <a href="https://blogs.oracle.com/javamagazine/post/records-come-to-java">Records Come to Java</a>
 */
@Getter
@Setter
@Deprecated
public class ProductDTO {
    private Integer id;

    @NotNull
    @Size(max = 50)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "1.00")
    private Double price;
}
