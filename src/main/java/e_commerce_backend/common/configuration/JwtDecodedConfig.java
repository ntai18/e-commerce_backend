package e_commerce_backend.common.configuration;

import com.nimbusds.jose.JOSEException;
import e_commerce_backend.features.authentication.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Objects;


@Configuration
@RequiredArgsConstructor
public class JwtDecodedConfig implements JwtDecoder {
    private final JwtService jwtService;
    private NimbusJwtDecoder nimbusJwtDecoder = null;
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            if(!(jwtService.verifyToken(token)))
                throw new RuntimeException("...");
            if (Objects.isNull(nimbusJwtDecoder)){
                SecretKey secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HS512");
                nimbusJwtDecoder = NimbusJwtDecoder
                        .withSecretKey(secretKeySpec)
                        .macAlgorithm(MacAlgorithm.HS512)
                        .build();
            }
        }
        catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }

        return nimbusJwtDecoder.decode(token);
    }
}
