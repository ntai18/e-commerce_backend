package e_commerce_backend.features.profile.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressResponse {
    private String name;
    private String address;
    private String city;
    private String description;
}
