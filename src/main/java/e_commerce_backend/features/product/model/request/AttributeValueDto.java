package e_commerce_backend.features.product.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeValueDto {
    @NotEmpty(message = "not empty")
    private String value;
}
