package e_commerce_backend.features.profile.service;

import e_commerce_backend.features.profile.model.dto.request.UserAddressRequest;
import e_commerce_backend.features.profile.model.dto.response.UserAddressResponse;

import java.util.List;

public interface UserAddressService {
    void addAddress(UserAddressRequest userAddressRequest, Long profileId);

    UserAddressResponse updateAddress(Long profileId, Long userAddressId, UserAddressRequest userAddressRequest);

    List<UserAddressResponse> getAddressByUserId(Long profileId);

    void deleteAddress(Long profileId, Long userAddressId);
}
