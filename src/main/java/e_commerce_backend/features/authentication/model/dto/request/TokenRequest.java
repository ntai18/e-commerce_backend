package e_commerce_backend.features.authentication.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenRequest {
    private String refreshToken;
}
