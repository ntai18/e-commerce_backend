package e_commerce_backend.features.shop.service;

public interface ShopAdminService {
    void activeShop(Long accountId);

    void lockShop(Long accountId);

    void unlockShop(Long accountId);

}
