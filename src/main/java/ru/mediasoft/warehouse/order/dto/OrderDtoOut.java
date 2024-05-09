package ru.mediasoft.warehouse.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderDtoOut {
    private UUID id;

    private List<OrderProductDtoInfo> products;

    private BigDecimal totalPrice;
}
