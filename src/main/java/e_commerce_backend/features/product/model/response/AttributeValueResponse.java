package e_commerce_backend.features.product.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeValueResponse {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Long attributeId;
    private String value;
}
