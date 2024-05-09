package ru.mediasoft.warehouse.order.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class OrderProductDtoInfo {
    private UUID uuid;
    private String name;
    private Integer quantity;
    private BigDecimal price;

}
