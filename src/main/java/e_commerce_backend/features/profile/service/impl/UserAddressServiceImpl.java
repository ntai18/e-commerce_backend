package e_commerce_backend.features.profile.service.impl;

import e_commerce_backend.common.exception.ECommerceException;
import e_commerce_backend.common.exception.ErrorCode;
import e_commerce_backend.features.profile.mapper.UserAddressMapper;
import e_commerce_backend.features.profile.model.dto.request.UserAddressRequest;
import e_commerce_backend.features.profile.model.dto.response.UserAddressResponse;
import e_commerce_backend.features.profile.model.entity.Profile;
import e_commerce_backend.features.profile.model.entity.UserAddress;
import e_commerce_backend.features.profile.repository.ProfileRepository;
import e_commerce_backend.features.profile.repository.UserAddressRepository;
import e_commerce_backend.features.profile.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {
    private final UserAddressRepository userAddressRepository;
    private final ProfileRepository profileRepository;
    private final UserAddressMapper userAddressMapper;


    @Override
    public void addAddress(UserAddressRequest userAddressRequest, Long profileId) {
        Profile profile = profileRepository.findByAccountId(profileId).orElseThrow(()-> new ECommerceException(ErrorCode.AUTH_01));
        UserAddress userAddress = new UserAddress();
        userAddressMapper.addAddress(userAddressRequest, userAddress);
        userAddress.setProfile(profile);
        userAddressRepository.save(userAddress);
    }

    @Override
    public UserAddressResponse updateAddress(Long profileId, Long userAddressId, UserAddressRequest userAddressRequest) {
        UserAddress userAddress = userAddressRepository.findByIdAndProfileId(userAddressId, profileId).orElseThrow(()-> new ECommerceException(ErrorCode.AUTH_01));
        userAddressMapper.requestToUserAddress(userAddressRequest, userAddress);
        userAddressRepository.save(userAddress);
        return userAddressMapper.userAddressToResponse(userAddress);
    }

    @Override
    public List<UserAddressResponse> getAddressByUserId(Long profileId) {
        List<UserAddress> listAddress = userAddressRepository.listUserAddressProfileId(profileId);
        if(listAddress.isEmpty())
            throw new ECommerceException(ErrorCode.AUTH_01);
        return userAddressMapper.listUserAddressToResponse(listAddress);
    }

    @Override
    public void deleteAddress(Long profileId, Long userAddressId) {
        UserAddress userAddress = userAddressRepository.findByIdAndProfileId(userAddressId, profileId).orElseThrow(()-> new ECommerceException(ErrorCode.AUTH_01));
        userAddressRepository.delete(userAddress);
    }
}
