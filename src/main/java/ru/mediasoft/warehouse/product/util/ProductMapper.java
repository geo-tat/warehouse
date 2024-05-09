package ru.mediasoft.warehouse.product.util;

import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderOut;
import ru.mediasoft.warehouse.product.dto.ProductDtoIn;
import ru.mediasoft.warehouse.product.dto.ProductDtoOut;
import ru.mediasoft.warehouse.product.model.Product;

public class ProductMapper {

   public static Product toEntity(ProductDtoIn dto) {
        return Product.builder()
                .name(dto.getName())
                .sku(dto.getSku())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .quantity(dto.getQuantity())
                .isAvailable(dto.isAvailable())
                .build();
    }

   public static ProductDtoOut toOut(Product product) {
        return ProductDtoOut.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .sku(product.getSku())
                .category(product.getCategory())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .created(product.getCreated())
                .updated(product.getUpdatedQuantity())
                .isAvailable(product.isAvailable())
                .build();
    }

    public static ProductDtoForOrderOut toProductDtoForOrderOut(Product product) {
       return ProductDtoForOrderOut.builder()
               .id(product.getId())
               .name(product.getName())
               .quantity(product.getQuantity())
               .price(product.getPrice())
               .build();


    }
}
