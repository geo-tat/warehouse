package ru.mediasoft.warehouse.order.service;

import ru.mediasoft.warehouse.order.model.OrderStatus;
import ru.mediasoft.warehouse.order.dto.OrderDtoIn;
import ru.mediasoft.warehouse.order.dto.OrderDtoOut;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderIn;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    UUID create(long customerId, OrderDtoIn dto);

    UUID update(long customerId, UUID orderId, List<ProductDtoForOrderIn> products);

    OrderDtoOut getByOrderId(long customerId, UUID orderId);

    void deleteByOrderId(long customerId, UUID orderId);

    String changeOrderStatus(UUID orderId, OrderStatus status);

    String confirm(long customerId, UUID orderId);
}
