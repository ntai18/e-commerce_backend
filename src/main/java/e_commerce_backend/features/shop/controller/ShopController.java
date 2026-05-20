package e_commerce_backend.features.shop.controller;

import e_commerce_backend.common.exception.ApiResponse;
import e_commerce_backend.common.util.UserInformation;
import e_commerce_backend.features.shop.model.dto.request.ShopRequest;
import e_commerce_backend.features.shop.model.dto.response.ShopResponse;
import e_commerce_backend.features.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerShop(@RequestBody ShopRequest shopRequest, @AuthenticationPrincipal UserInformation userInformation) {
        shopService.registerShop(shopRequest, userInformation.getAccountId());
        return ResponseEntity.ok(ApiResponse.success("success"));
    }

    @PatchMapping("/update/me")
    public ResponseEntity<ApiResponse<ShopResponse>> updateShop(@RequestBody ShopRequest shopRequest, @AuthenticationPrincipal Jwt jwt){
        Long accountId = jwt.getClaim("id");
        return ResponseEntity.ok(ApiResponse.successData("Successfully updated shop...!!!", shopService.updateShop(shopRequest, accountId)));
    }

    @DeleteMapping("/delete/me")
    public ResponseEntity<ApiResponse> deleteShop(@RequestBody ShopRequest shopRequest, @AuthenticationPrincipal Jwt jwt){
        Long accountId = jwt.getClaim("id");
        shopService.deleteShop(shopRequest, accountId);
        return ResponseEntity.ok(ApiResponse.success("Successfully delete shop...!!!"));
    }



}
