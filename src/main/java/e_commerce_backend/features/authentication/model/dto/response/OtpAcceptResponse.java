package e_commerce_backend.features.authentication.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OtpAcceptResponse {
    private String token;
}
