package e_commerce_backend.features.product.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class VariantProductResponse {
    private Long id;
    private Double price;
    private Integer stock;
    private String sku;
    private String imageUrl;
    private List<AttributeValueResponse> value = new ArrayList<>();
}
