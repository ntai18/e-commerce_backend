package e_commerce_backend.features.product.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    @NotEmpty(message = "not empty title ")
    private String title;
    @NotEmpty(message = "not empty des")
    private String description;
    private List<AttributeDto> attribute;
    @Valid
    private List<VariantProductDto> variantProduct;
}
