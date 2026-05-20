package e_commerce_backend.features.product.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import e_commerce_backend.common.type.ProductStatusType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductResponse {
    private Long id;
    private Long shopId;
    private String title;
    private String description;
    private ProductStatusType status;
    private List<AttributeResponse> attribute = new ArrayList<>();
    private List<VariantProductResponse> variant = new ArrayList<>();
}
