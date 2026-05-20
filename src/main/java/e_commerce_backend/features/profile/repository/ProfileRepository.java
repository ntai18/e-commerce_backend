package e_commerce_backend.features.profile.repository;

import e_commerce_backend.features.profile.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {
    @Query(value = "SELECT p FROM Profile p WHERE p.accountId = :accountId")
    Optional<Profile> findByAccountId(Long accountId);




}
