package e_commerce_backend.features.product.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "variant_product")
@Getter
@Setter
public class VariantProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinTable(name = "variant_attribute_value",
               joinColumns = @JoinColumn(name = "variant_id"),
               inverseJoinColumns = @JoinColumn(name = "attribute_value_id"))
    private List<AttributeValue> attributeValue = new ArrayList<>();
    @Column(name = "image_url")
    private String imageUrl;
    private BigDecimal price;
    private Integer stock;
    private String sku;
}
