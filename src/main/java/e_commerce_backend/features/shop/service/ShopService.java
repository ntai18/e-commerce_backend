package e_commerce_backend.features.shop.service;

import e_commerce_backend.features.shop.model.dto.request.ShopRequest;
import e_commerce_backend.features.shop.model.dto.response.ShopResponse;

public interface ShopService {
    void registerShop(ShopRequest shopRequest, Long accountId);

    ShopResponse updateShop(ShopRequest shopRequest, Long accountId);

    void deleteShop(ShopRequest shopRequest, Long accountId);
}
