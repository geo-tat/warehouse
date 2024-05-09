package ru.mediasoft.warehouse.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductDtoForOrderOut {

    private UUID id;

    private String name;

    private int quantity;

    private BigDecimal price;

}
