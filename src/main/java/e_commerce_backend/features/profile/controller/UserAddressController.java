package e_commerce_backend.features.profile.controller;

import e_commerce_backend.common.exception.ApiResponse;
import e_commerce_backend.features.profile.model.dto.request.UserAddressRequest;
import e_commerce_backend.features.profile.model.dto.response.UserAddressResponse;
import e_commerce_backend.features.profile.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class UserAddressController {
    private final UserAddressService userAddressService;

    @PostMapping("/add/me")
    public ResponseEntity<ApiResponse> addAddress(@RequestBody UserAddressRequest userAddressRequest, @AuthenticationPrincipal Jwt jwt)  {
        Long id = jwt.getClaim("id");
        userAddressService.addAddress(userAddressRequest, id);
        return ResponseEntity.ok(ApiResponse.success("success adding new address !!!"));
    }

    @PatchMapping("/update/me/{address_id}")
    public ResponseEntity<ApiResponse<UserAddressResponse>> updateAddress(@RequestBody UserAddressRequest userAddressRequest, @AuthenticationPrincipal Jwt jwt, @PathVariable(name = "address_id") Long addressId) {
        Long id = jwt.getClaim("id");
        return ResponseEntity.ok(ApiResponse.successData("Success updating address...!!!!",  userAddressService.updateAddress(id, addressId, userAddressRequest)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getAddressMe(@AuthenticationPrincipal Jwt jwt) {
        Long id = jwt.getClaim("id");
        return ResponseEntity.ok(ApiResponse.successData("get all address me...!!!!", userAddressService.getAddressByUserId(id)));
    }

    @DeleteMapping("/delete/me/{address_id}")
    public ResponseEntity<ApiResponse> deleteAddress(@AuthenticationPrincipal Jwt jwt, @PathVariable(name = "address_id") Long addressId) {
        Long id = jwt.getClaim("id");
        userAddressService.deleteAddress(id, addressId);
        return ResponseEntity.ok(ApiResponse.success("delete address ....!!!!"));
    }

}
