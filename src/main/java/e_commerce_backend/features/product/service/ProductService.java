package e_commerce_backend.features.product.service;

import e_commerce_backend.features.product.model.request.ProductDto;
import e_commerce_backend.features.product.model.response.ProductResponse;

import java.util.List;

public interface ProductService {
    void createProduct(ProductDto productRequest, Long accountId);

    List<ProductResponse> getListProductByShop(Long productId);

    ProductResponse getProductDetailById(Long accountId, Long productId);
}
