package e_commerce_backend.features.authentication.service;

import e_commerce_backend.features.authentication.model.dto.request.AcceptOtpRequest;
import e_commerce_backend.features.authentication.model.dto.request.AccountRequest;
import e_commerce_backend.features.authentication.model.dto.request.ResetPasswordRequest;
import e_commerce_backend.features.authentication.model.dto.request.TokenRequest;
import e_commerce_backend.features.authentication.model.dto.response.OtpAcceptResponse;
import e_commerce_backend.features.authentication.model.dto.response.TokenResponse;


public interface AccountService {
    void createAccount(AccountRequest accountRequest);

    TokenResponse login(AccountRequest accountRequest);

    TokenResponse refreshToken(TokenRequest tokenRequest);

    void logout(TokenRequest tokenRequest);

    void forgotPassword(AccountRequest accountRequest);

    OtpAcceptResponse acceptOtp(AcceptOtpRequest acceptOtpRequest);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);
}
