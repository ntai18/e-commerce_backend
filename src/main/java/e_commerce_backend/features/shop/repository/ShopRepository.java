package e_commerce_backend.features.shop.repository;


import e_commerce_backend.features.shop.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    @Query(value = "SELECT s FROM Shop s WHERE s.accountId = :accountId")
    Optional<Shop> findByAccountId(Long accountId);
}
