package e_commerce_backend.features.shop.mapper;

import e_commerce_backend.features.shop.model.dto.request.ShopRequest;
import e_commerce_backend.features.shop.model.dto.response.ShopResponse;
import e_commerce_backend.features.shop.model.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ShopMapper {
    void requestToShop(ShopRequest shopRequest, @MappingTarget Shop shop);

    ShopResponse shopToResponse(Shop shop);
}
