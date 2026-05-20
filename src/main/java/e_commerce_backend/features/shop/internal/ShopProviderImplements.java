package e_commerce_backend.features.shop.internal;

import e_commerce_backend.common.exception.ECommerceException;
import e_commerce_backend.common.exception.ErrorCode;
import e_commerce_backend.features.contract.ShopProvider;
import e_commerce_backend.features.shop.model.entity.Shop;
import e_commerce_backend.features.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopProviderImplements implements ShopProvider {
    private final ShopRepository shopRepository;

    @Override
    public Long getIdShop(Long accountId) {
        Shop shop = shopRepository.findByAccountId(accountId).orElseThrow(() -> new ECommerceException(ErrorCode.AUTH_01));
        return shop.getId();
    }
}
