package e_commerce_backend.features.product.repository;

import e_commerce_backend.common.type.ProductStatusType;
import e_commerce_backend.features.product.model.response.AttributeResponse;
import e_commerce_backend.features.product.model.response.AttributeValueResponse;
import e_commerce_backend.features.product.model.response.ProductResponse;
import e_commerce_backend.features.product.model.response.VariantProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<ProductResponse> getProductByShopId(Long shopId) {
        String sqlProduct = """
                SELECT p.id , p.shop_id , p.description , p.title , p.status
                FROM product p
                WHERE p.shop_id = :shopId
                """;
        MapSqlParameterSource productParam = new MapSqlParameterSource("shopId", shopId);
        List<ProductResponse> productResponseList =  jdbcTemplate.query(sqlProduct, productParam, (rs, rowNum) -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(rs.getLong("id"));
            productResponse.setShopId(rs.getLong("shop_id"));
            productResponse.setDescription(rs.getString("description"));
            productResponse.setTitle(rs.getString("title"));
            productResponse.setStatus(ProductStatusType.valueOf(rs.getString("status").toUpperCase()));
            return productResponse;
            }
        );
        return productResponseList;
    }


    // productDetail
    public ProductResponse getProductDetailByProductId(Long productId) {
        String sqlProduct = """
                SELECT p.id , p.shop_id , p.description , p.title , p.status
                FROM product p
                WHERE p.id = :productId
                """;
        MapSqlParameterSource productParamP = new MapSqlParameterSource("productId", productId);
        ProductResponse productResponse = (ProductResponse) jdbcTemplate.queryForObject(sqlProduct, productParamP, (rs, rowNum) ->  {
            ProductResponse productResponse1 = new ProductResponse();
            productResponse1.setId(rs.getLong("id"));
            productResponse1.setShopId(rs.getLong("shop_id"));
            productResponse1.setDescription(rs.getString("description"));
            productResponse1.setTitle(rs.getString("title"));
            productResponse1.setStatus(ProductStatusType.valueOf(rs.getString("status").toUpperCase()));
                return productResponse1;
        });

        String sqlAttribute = """
                SELECT a.id, a.attribute_name
                FROM attribute a
                WHERE a.product_id = :productId
                """;
        MapSqlParameterSource productParamA = new MapSqlParameterSource("productId", productId);
        List<AttributeResponse> attributeResponseList = (List<AttributeResponse>) jdbcTemplate.query(sqlAttribute, productParamA, (rs, rowNum) -> {
            AttributeResponse attributeResponse = new AttributeResponse();
            attributeResponse.setId(rs.getLong("id"));
            attributeResponse.setAttributeName(rs.getString("attribute_name"));
            return attributeResponse;

        });

        List<Long> attributeId = attributeResponseList.stream().map(AttributeResponse::getId).toList();
        String sqlAttributeValue = """
                SELECT *
                FROM attribute_value av
                WHERE av.attribute_id IN (:attributeId)
                """;
        MapSqlParameterSource productParamAV = new MapSqlParameterSource("attributeId", attributeId);
        List<AttributeValueResponse> attributeValueResponse = (List<AttributeValueResponse>) jdbcTemplate.query(sqlAttributeValue, productParamAV, (rs, rowNum) -> {
            AttributeValueResponse attributeValueResponse1 = new AttributeValueResponse();
            attributeValueResponse1.setId(rs.getLong("id"));
            attributeValueResponse1.setAttributeId(rs.getLong("attribute_id"));
            attributeValueResponse1.setValue(rs.getString("value"));
            return attributeValueResponse1;
        });
        Map<Long, AttributeValueResponse> mapIdAndValue = attributeValueResponse.stream().collect(Collectors.toMap(AttributeValueResponse::getId, Function.identity()));

        Map<Long, List<AttributeValueResponse>> groupByAttributeId = (Map<Long, List<AttributeValueResponse>>) attributeValueResponse.stream().collect(Collectors.groupingBy(AttributeValueResponse::getAttributeId));
        for (AttributeResponse attributeResponse : attributeResponseList) {
            Long id = attributeResponse.getId();
            List<AttributeValueResponse> attributeValueResponseList = groupByAttributeId.getOrDefault(id, new ArrayList<>());
            attributeResponse.setValue(attributeValueResponseList);
        }
        String sqlVariant = """
                SELECT vp.id, vp.stock, vp.price, vp.image_url, vp.sku
                FROM variant_product vp
                WHERE vp.product_id = :productId
                """;
        MapSqlParameterSource productParamV = new MapSqlParameterSource("productId", productId);
        List<VariantProductResponse> variantProductResponseList = (List<VariantProductResponse>) jdbcTemplate.query(sqlVariant, productParamV, (rs, rowNum) -> {
            VariantProductResponse variantProductResponse = new VariantProductResponse();
            variantProductResponse.setId(rs.getLong("id"));
            variantProductResponse.setPrice(rs.getDouble("price"));
            variantProductResponse.setImageUrl(rs.getString("image_url"));
            variantProductResponse.setSku(rs.getString("sku"));
            variantProductResponse.setStock(rs.getInt("stock"));
            return variantProductResponse;
        });

        List<Long> variantId = variantProductResponseList.stream().map(VariantProductResponse::getId).toList();
        String sqlVariantValue = """
                SELECT vav.attribute_value_id, vav.variant_id
                FROM variant_attribute_value vav
                WHERE vav.variant_id IN (:variantId)
                """;
        MapSqlParameterSource productParamVAV = new MapSqlParameterSource("variantId", variantId);
        Map<Long, List<Long>> listAttributeValueId = jdbcTemplate.query(sqlVariantValue, productParamVAV, rs -> {
                Map<Long, List<Long>> map = new HashMap<>();
                    while (rs.next()) {
                        Long varId = rs.getObject("variant_id", Long.class);
                        Long attrId = rs.getObject("attribute_value_id", Long.class);
                        map.computeIfAbsent(varId, k -> new ArrayList<>()).add(attrId);
                    }
                    return map;
        });
        for (VariantProductResponse variantProductResponse : variantProductResponseList) {
            if (variantProductResponse.getId() != null) {
                 List<Long> listIdAttributeValue = listAttributeValueId.getOrDefault(variantProductResponse.getId(), new ArrayList<>());
                 List<AttributeValueResponse> list = new ArrayList<>();
                 for (Long id : listIdAttributeValue) {
                     AttributeValueResponse attributeValue = mapIdAndValue.get(id);
                     if (attributeValue != null) {
                         list.add(attributeValue);
                     }
                 }
                 variantProductResponse.setValue(list);
            }
        }
        productResponse.setVariant(variantProductResponseList);
        productResponse.setAttribute(attributeResponseList);
        return productResponse;
    }
}

