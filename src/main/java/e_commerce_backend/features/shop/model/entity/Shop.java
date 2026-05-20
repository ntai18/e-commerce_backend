package e_commerce_backend.features.shop.model.entity;

import e_commerce_backend.common.type.StatusShopType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "shop")
@Getter
@Setter
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", unique = true, nullable = false)
    private Long accountId;

    @Column(name = "shop_name")
    private String shopName;
    @Column(name = "address_from")
    private String addressFrom;
    private String description;
    @Enumerated(EnumType.STRING)
    private StatusShopType shopStatus;

}
