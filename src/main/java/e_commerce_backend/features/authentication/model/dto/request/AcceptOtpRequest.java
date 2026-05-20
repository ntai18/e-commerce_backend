package e_commerce_backend.features.authentication.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptOtpRequest {
    private String email;
    private String otp;
}
