package e_commerce_backend.features.profile.mapper;

import e_commerce_backend.features.profile.model.dto.request.UserAddressRequest;
import e_commerce_backend.features.profile.model.dto.response.UserAddressResponse;
import e_commerce_backend.features.profile.model.entity.UserAddress;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserAddressMapper {
    void addAddress(UserAddressRequest userAddressRequest, @MappingTarget UserAddress userAddress);

    void requestToUserAddress(UserAddressRequest userAddressRequest, @MappingTarget UserAddress userAddress);

    UserAddressResponse userAddressToResponse(UserAddress userAddress);

    List<UserAddressResponse> listUserAddressToResponse(List<UserAddress> userAddress);


}
