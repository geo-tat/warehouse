package ru.mediasoft.warehouse.product.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProductDtoForOrderOut {
    private UUID uuid;
    private String name;
    private Integer quantity;
    private BigDecimal price;

}
