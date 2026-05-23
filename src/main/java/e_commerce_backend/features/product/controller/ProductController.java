package e_commerce_backend.features.product.controller;

import e_commerce_backend.common.exception.ApiResponse;
import e_commerce_backend.common.util.UserInformation;
import e_commerce_backend.features.product.model.request.ProductDto;
import e_commerce_backend.features.product.model.response.ProductResponse;
import e_commerce_backend.features.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create/me")
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody ProductDto productDto, @AuthenticationPrincipal UserInformation user) {
        productService.createProduct(productDto, user.getAccountId());
        return ResponseEntity.ok(ApiResponse.success("create product successfully"));
    }

    @PutMapping("/update/{product-id}/me")
    public void updateProduct(){

    }
    @DeleteMapping("/delete/{product-id}")
    public void deleteProduct(){

    }
    @GetMapping("/{product-id}")
    public void product(){

    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> products(@AuthenticationPrincipal UserInformation user){
        return ResponseEntity.ok().body(ApiResponse.successData("Product list user "+ user.getAccountId(), productService.getListProductByShop(user.getAccountId())));
    }

    @GetMapping("/shop/detail/{product-id}")
    public ResponseEntity<ApiResponse<ProductResponse>> productDetail(@PathVariable(name = "product-id") Long productId, @AuthenticationPrincipal UserInformation user){
        return ResponseEntity.ok().body(ApiResponse.successData("Detail Product ---", productService.getProductDetailById(user.getAccountId(), productId)));
    }

}

