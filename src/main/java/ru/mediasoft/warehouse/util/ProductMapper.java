package ru.mediasoft.warehouse.util;

import ru.mediasoft.warehouse.dto.ProductDtoIn;
import ru.mediasoft.warehouse.dto.ProductDtoInfo;
import ru.mediasoft.warehouse.dto.ProductDtoOut;
import ru.mediasoft.warehouse.model.Product;
import ru.mediasoft.warehouse.service.currency.CurrencyProvider;

import java.math.RoundingMode;

public class ProductMapper {

   public static Product toEntity(ProductDtoIn dto) {
        return Product.builder()
                .name(dto.getName())
                .sku(dto.getSku())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .quantity(dto.getQuantity())
                .build();
    }

   public static ProductDtoOut toOut(Product product, CurrencyProvider provider) {
        return ProductDtoOut.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice().divide(provider.getCurrencyFromWeb(), RoundingMode.HALF_UP))
                .sku(product.getSku())
                .category(product.getCategory())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .created(product.getCreated())
                .updated(product.getUpdatedQuantity())
                .currencyType(provider.getCurrencyType())
                .build();
    }

    public static ProductDtoInfo toInfo(Product product) {
       return ProductDtoInfo.builder()
               .id(product.getId())
               .name(product.getName())
               .build();
    }
}
