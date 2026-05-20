package e_commerce_backend.features.product.model.response;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AttributeResponse {
    private Long id;
    private String attributeName;
    private List<AttributeValueResponse> value = new ArrayList<>();
}
