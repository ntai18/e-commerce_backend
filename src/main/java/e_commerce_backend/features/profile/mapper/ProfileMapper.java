package e_commerce_backend.features.profile.mapper;

import e_commerce_backend.features.profile.model.dto.request.ProfileRequest;
import e_commerce_backend.features.profile.model.dto.response.ProfileResponse;
import e_commerce_backend.features.profile.model.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProfileMapper {
    void updateProfile(ProfileRequest profileRequest, @MappingTarget Profile profile);

    ProfileResponse profileToProfileResponse(Profile profile);
}
