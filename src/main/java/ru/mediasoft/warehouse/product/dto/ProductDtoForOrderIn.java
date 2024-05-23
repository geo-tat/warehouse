package ru.mediasoft.warehouse.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductDtoForOrderIn {
    private UUID id;

    private int quantity;
}
