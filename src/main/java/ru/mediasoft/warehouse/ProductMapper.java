package ru.mediasoft.warehouse;

public class ProductMapper {

    static Product toEntity(ProductDtoIn dto) {
        return Product.builder()
                .name(dto.getName())
                .sku(dto.getSku())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .quantity(dto.getQuantity())
                .build();
    }

    static ProductDtoOut toOut(Product product) {
        return ProductDtoOut.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .sku(product.getSku())
                .category(product.getCategory())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .created(product.getCreated())
                .updated(product.getUpdated())
                .build();
    }
}
