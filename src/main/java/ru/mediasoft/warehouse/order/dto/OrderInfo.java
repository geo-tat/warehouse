package ru.mediasoft.warehouse.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.mediasoft.warehouse.customer.dto.CustomerInfo;
import ru.mediasoft.warehouse.order.model.OrderStatus;

import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderInfo {

    private UUID id;

    private CustomerInfo customer;

    private OrderStatus status;

    private String deliveryAddress;

    private Integer quantity;
}
