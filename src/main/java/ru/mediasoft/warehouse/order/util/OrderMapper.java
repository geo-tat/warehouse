package ru.mediasoft.warehouse.order.util;

import ru.mediasoft.warehouse.order.dto.OrderDtoOut;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderOut;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderMapper {
    public static OrderDtoOut toOrderInfoDto(List<ProductDtoForOrderOut> list, UUID orderId) {
        return OrderDtoOut.builder()
                .products(list)
                .id(orderId)
                .totalPrice(BigDecimal.valueOf(list.stream()
                        .mapToInt(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())).intValue())
                        .sum()))
                .build();
    }
}
