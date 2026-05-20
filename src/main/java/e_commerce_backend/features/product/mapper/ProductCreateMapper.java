package e_commerce_backend.features.product.mapper;

import e_commerce_backend.common.exception.ECommerceException;
import e_commerce_backend.common.exception.ErrorCode;
import e_commerce_backend.features.product.model.request.AttributeDto;
import e_commerce_backend.features.product.model.request.AttributeValueDto;
import e_commerce_backend.features.product.model.request.ProductDto;
import e_commerce_backend.features.product.model.request.VariantProductDto;
import e_commerce_backend.features.product.model.entity.Attribute;
import e_commerce_backend.features.product.model.entity.AttributeValue;
import e_commerce_backend.features.product.model.entity.Product;
import e_commerce_backend.features.product.model.entity.VariantProduct;
import org.mapstruct.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductCreateMapper {
    Product productToEntity(ProductDto productDto,Long shopId);

    Attribute attributeToEntity(AttributeDto attributeDto);

    AttributeValue attributeValueToEntity(AttributeValueDto attributeValueDto);
    //@Mapping(target = "attributeValue", ignore = true)
    VariantProduct variantToEntity(VariantProductDto variantProductDto);

    @AfterMapping
    default void mapping(@MappingTarget Product product) {
        Map<String, Object> map = new HashMap<>();
        if(product.getAttribute() != null) {
           for(Attribute attribute : product.getAttribute()) {
               attribute.setProduct(product);
               for(AttributeValue attributeValue : attribute.getValue()) {
                   attributeValue.setAttribute(attribute);
                   map.put(attributeValue.getValue(), attributeValue);
               }
           }
        }

        if(product.getVariantProduct() != null) {
            for(VariantProduct variantProduct : product.getVariantProduct()) {
                variantProduct.setProduct(product);
                List<AttributeValue> attributeValueList = new ArrayList<>();
                for(AttributeValue attributeValueKey : variantProduct.getAttributeValue()){
                    AttributeValue attributeValueObject = (AttributeValue) map.get(attributeValueKey.getValue());
                    if(attributeValueObject == null)
                        throw new ECommerceException(ErrorCode.PRODUCT);
                    if (attributeValueList.contains(attributeValueObject))
                        throw new ECommerceException(ErrorCode.PRODUCT);
                    attributeValueList.add(attributeValueObject);
                }
                variantProduct.setAttributeValue(attributeValueList);
            }
        }
    }


}