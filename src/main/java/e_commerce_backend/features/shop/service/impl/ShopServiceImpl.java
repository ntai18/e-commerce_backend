package e_commerce_backend.features.shop.service.impl;

import e_commerce_backend.common.exception.ECommerceException;
import e_commerce_backend.common.exception.ErrorCode;
import e_commerce_backend.features.shop.mapper.ShopMapper;
import e_commerce_backend.features.shop.model.dto.request.ShopRequest;
import e_commerce_backend.features.shop.model.dto.response.ShopResponse;
import e_commerce_backend.features.shop.model.entity.Shop;
import e_commerce_backend.features.shop.repository.ShopRepository;
import e_commerce_backend.features.shop.service.ShopService;
import e_commerce_backend.common.type.StatusShopType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    @Override
    public void registerShop(ShopRequest shopRequest, Long accountId) {
        Shop shop = new Shop();
        shop.setAccountId(accountId);
        shop.setShopStatus(StatusShopType.PENDING);
        shopMapper.requestToShop(shopRequest, shop);
        shopRepository.save(shop);
    }

    @Override
    public ShopResponse updateShop(ShopRequest shopRequest, Long accountId) {
        Shop shop = shopRepository.findByAccountId(accountId).orElseThrow(()-> new ECommerceException(ErrorCode.AUTH_01));
        shopMapper.requestToShop(shopRequest, shop);
        shopRepository.save(shop);
        return shopMapper.shopToResponse(shop);
    }

    @Override
    public void deleteShop(ShopRequest shopRequest, Long accountId) {
        Shop shop = shopRepository.findByAccountId(accountId).orElseThrow(()-> new ECommerceException(ErrorCode.AUTH_01));
        shop.setShopStatus(StatusShopType.DELETE);
        shopRepository.save(shop);
    }
}
