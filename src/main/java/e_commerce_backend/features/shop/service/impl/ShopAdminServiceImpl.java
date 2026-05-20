package e_commerce_backend.features.shop.service.impl;

import e_commerce_backend.common.exception.ECommerceException;
import e_commerce_backend.common.exception.ErrorCode;
import e_commerce_backend.features.shop.model.entity.Shop;
import e_commerce_backend.features.shop.repository.ShopRepository;
import e_commerce_backend.features.shop.service.ShopAdminService;
import e_commerce_backend.common.type.StatusShopType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopAdminServiceImpl implements ShopAdminService {
    private final ShopRepository shopRepository;

    @Override
    public void activeShop(Long accountId) {
        Shop shop = shopRepository.findByAccountId(accountId).orElseThrow(()-> new ECommerceException(ErrorCode.AUTH_01));
        if (shop.getShopStatus() == StatusShopType.ACTIVE)
            throw new ECommerceException(ErrorCode.AUTH_01);
        if (shop.getShopStatus() == StatusShopType.LOCK)
            throw new ECommerceException(ErrorCode.AUTH_02);
        shop.setShopStatus(StatusShopType.ACTIVE);
        shopRepository.save(shop);
    }

    @Override
    public void lockShop(Long accountId) {
        Shop shop = shopRepository.findByAccountId(accountId).orElseThrow(()-> new ECommerceException(ErrorCode.AUTH_01));
        if (shop.getShopStatus() == StatusShopType.LOCK)
            throw new ECommerceException(ErrorCode.AUTH_02);
        shop.setShopStatus(StatusShopType.LOCK);
        shopRepository.save(shop);
    }

    @Override
    public void unlockShop(Long accountId) {
        Shop shop = shopRepository.findByAccountId(accountId).orElseThrow(()-> new ECommerceException(ErrorCode.AUTH_01));
        if (shop.getShopStatus() == StatusShopType.LOCK) {
            shop.setShopStatus(StatusShopType.ACTIVE);
            shopRepository.save(shop);
        }else
            throw new ECommerceException(ErrorCode.AUTH_02);
    }
}
