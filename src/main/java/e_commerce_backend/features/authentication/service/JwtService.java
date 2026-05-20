package e_commerce_backend.features.authentication.service;


import com.nimbusds.jose.JOSEException;
import e_commerce_backend.features.authentication.model.entity.Account;

import java.text.ParseException;

public interface JwtService {
    String generateAccessToken(Account user);

    String generateRefreshToken();

    boolean verifyToken(String token) throws ParseException, JOSEException;
}
