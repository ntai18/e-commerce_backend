package e_commerce_backend.features.authentication.controller;

import e_commerce_backend.features.authentication.model.dto.request.AcceptOtpRequest;
import e_commerce_backend.features.authentication.model.dto.request.AccountRequest;
import e_commerce_backend.features.authentication.model.dto.request.ResetPasswordRequest;
import e_commerce_backend.features.authentication.model.dto.request.TokenRequest;
import e_commerce_backend.features.authentication.model.dto.response.OtpAcceptResponse;
import e_commerce_backend.features.authentication.model.dto.response.TokenResponse;
import e_commerce_backend.features.authentication.service.AccountService;
import e_commerce_backend.common.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody AccountRequest accountRegisteredRequest) {
        accountService.createAccount(accountRegisteredRequest);
        return ResponseEntity.ok(ApiResponse.success("Successfully created account...!!!"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody AccountRequest userSignUpRequest) {
        return ResponseEntity.ok(ApiResponse.successData("Login success", accountService.login(userSignUpRequest)));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(ApiResponse.successData("Refresh token success", accountService.refreshToken(tokenRequest)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@RequestBody TokenRequest tokenRequest) {
        accountService.logout(tokenRequest);
        return ResponseEntity.ok(ApiResponse.success("logout success !!"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody AccountRequest accountRequest) {
        accountService.forgotPassword(accountRequest);
        return ResponseEntity.ok(ApiResponse.success("forgot password success!!"));
    }

    @PostMapping("/accept-otp")
    public ResponseEntity<ApiResponse<OtpAcceptResponse>> acceptOtp(@RequestBody AcceptOtpRequest acceptOtpRequest) {
        return ResponseEntity.ok(ApiResponse.successData("accept otp success!!", accountService.acceptOtp(acceptOtpRequest)));
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        accountService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok(ApiResponse.success("Reset password success!!!"));
    }


}
