package e_commerce_backend.features.product.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VariantProductDto {
    private Long id;
    @NotNull
    @Min(value = 1, message = ">0")
    private Double price;
    @NotNull
    @Min(value = 1, message = ">0")
    private Integer stock;
    private List<AttributeValueDto> attributeValue = new ArrayList<>();
    @NotBlank(message = "not Blank SKU !!")
    @Size(min = 5, max = 9, message = " 5 - 9 char and not char-special")
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "sku not valid")
    private String sku;
    private String imageUrl;
}
