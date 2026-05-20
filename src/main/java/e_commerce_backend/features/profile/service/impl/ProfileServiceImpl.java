package e_commerce_backend.features.profile.service.impl;

import e_commerce_backend.features.authentication.events.UserRegisteredEvent;
import e_commerce_backend.common.exception.ECommerceException;
import e_commerce_backend.common.exception.ErrorCode;
import e_commerce_backend.features.profile.mapper.ProfileMapper;
import e_commerce_backend.features.profile.model.dto.request.ProfileRequest;
import e_commerce_backend.features.profile.model.dto.response.ProfileResponse;
import e_commerce_backend.features.profile.model.entity.Profile;
import e_commerce_backend.features.profile.repository.ProfileRepository;
import e_commerce_backend.features.profile.service.ProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Transactional
    @EventListener
    public void createProfile(UserRegisteredEvent userRegisteredEvent) {
        Profile profile = new Profile();
        profile.setAccountId(userRegisteredEvent.getAccountId());
        profileRepository.save(profile);
    }

    @Override
    public ProfileResponse updateProfile(ProfileRequest profileRequest, Long accountId) {
        Profile profile =
                profileRepository.findByAccountId(accountId).orElseThrow(()-> new ECommerceException(ErrorCode.AUTH_01));
        profileMapper.updateProfile(profileRequest, profile);
        profileRepository.save(profile);
        return profileMapper.profileToProfileResponse(profile);
    }
}
