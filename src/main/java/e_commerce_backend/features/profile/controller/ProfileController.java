package e_commerce_backend.features.profile.controller;

import e_commerce_backend.common.exception.ApiResponse;
import e_commerce_backend.features.profile.model.dto.request.ProfileRequest;
import e_commerce_backend.features.profile.model.dto.response.ProfileResponse;
import e_commerce_backend.features.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<ProfileResponse>> me(@RequestBody ProfileRequest profileRequest, @AuthenticationPrincipal Jwt jwt) {
        Long accountId = jwt.getClaim("id");
        return ResponseEntity.ok(ApiResponse.successData("update profile success !!", profileService.updateProfile(profileRequest, accountId)));
    }

}
