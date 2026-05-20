package e_commerce_backend.features.profile.repository;

import e_commerce_backend.features.profile.model.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    @Query(value = "SELECT u FROM UserAddress u WHERE u.id = :userAddressId AND u.profile.accountId = :profileId ")
    Optional<UserAddress>  findByIdAndProfileId(Long userAddressId, Long profileId);

    @Query(value = "SELECT u FROM UserAddress u WHERE u.profile.accountId = :profileId")
    List<UserAddress> listUserAddressProfileId(Long profileId);


}
