package e_commerce_backend.features.profile.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressRequest {
    private String name;
    private String address;
    private String city;
    private String description;

}
