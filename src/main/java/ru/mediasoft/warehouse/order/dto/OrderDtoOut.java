package ru.mediasoft.warehouse.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderOut;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderDtoOut {
    private UUID id;

    private List<ProductDtoForOrderOut> products;

    private BigDecimal totalPrice;
}
