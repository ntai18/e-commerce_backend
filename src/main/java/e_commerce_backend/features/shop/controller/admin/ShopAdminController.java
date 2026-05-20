package e_commerce_backend.features.shop.controller.admin;

import e_commerce_backend.common.exception.ApiResponse;
import e_commerce_backend.features.shop.service.ShopAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/shop/admin")
@RequiredArgsConstructor
public class ShopAdminController {
    private final ShopAdminService shopAdminService;

    @PatchMapping("/active/{account-id}")
    public ResponseEntity<ApiResponse> activeShop(@PathVariable(name = "account-id") Long accountId) {
        shopAdminService.activeShop(accountId);
        log.info("...........");
        return ResponseEntity.ok(ApiResponse.success("Shop Admin has been activated"));
    }

    @PatchMapping("/lock/{account-id}")
    public ResponseEntity<ApiResponse> lockShop(@PathVariable(name = "account-id") Long accountId) {
        shopAdminService.lockShop(accountId);
        return ResponseEntity.ok(ApiResponse.success("Shop Admin has been locked"));
    }

    @PatchMapping("/unlock/{account-id}")
    public ResponseEntity<ApiResponse> unlockShop(@PathVariable(name = "account-id") Long accountId) {
        shopAdminService.unlockShop(accountId);
        return ResponseEntity.ok(ApiResponse.success("Shop Admin has been unbanned"));
    }

}
