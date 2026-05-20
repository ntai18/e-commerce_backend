package e_commerce_backend.features.product.service.impl;

import e_commerce_backend.common.type.ProductStatusType;
import e_commerce_backend.features.contract.ShopProvider;
import e_commerce_backend.features.product.mapper.ProductCreateMapper;
import e_commerce_backend.features.product.model.entity.Product;
import e_commerce_backend.features.product.model.request.ProductDto;
import e_commerce_backend.features.product.model.response.ProductResponse;
import e_commerce_backend.features.product.repository.ProductQueryRepository;
import e_commerce_backend.features.product.repository.ProductRepository;
import e_commerce_backend.features.product.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ShopProvider shopProvider;
    private final ProductQueryRepository productQueryRepository;
    private final ProductCreateMapper productCreateMapper;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void createProduct(ProductDto productDto, Long accountId) {
        log.info("Creating product with");
        Long shopId = shopProvider.getIdShop(accountId);
        Product product = productCreateMapper.productToEntity(productDto, shopId);
        product.setStatus(ProductStatusType.PENDING);
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> getProductByShop(Long accountId) {
        return productQueryRepository.getProductByShopId(shopProvider.getIdShop(accountId));
    }

    @Override
    public ProductResponse getProductDetailById(Long accountId, Long productId) {
        return productQueryRepository.getProductDetailByProductId(productId);
    }
}
