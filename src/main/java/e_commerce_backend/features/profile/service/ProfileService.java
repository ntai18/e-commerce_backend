package e_commerce_backend.features.profile.service;

import e_commerce_backend.features.profile.model.dto.request.ProfileRequest;
import e_commerce_backend.features.profile.model.dto.response.ProfileResponse;

public interface ProfileService {
    ProfileResponse updateProfile(ProfileRequest profileRequest, Long accountId);
}
