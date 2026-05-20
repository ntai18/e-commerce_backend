package e_commerce_backend.features.shop.model.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopRequest {
    private String shopName;
    private String addressFrom;
    private String description;
}
