package e_commerce_backend.features.product.model.request;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AttributeDto {
    private String attributeName;
    @Valid
    private List<AttributeValueDto> value = new ArrayList<>();
}
