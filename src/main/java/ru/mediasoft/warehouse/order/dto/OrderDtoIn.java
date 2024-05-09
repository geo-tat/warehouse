package ru.mediasoft.warehouse.order.dto;

import lombok.*;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderIn;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderDtoIn {

    private String deliveryAddress;

    private List<ProductDtoForOrderIn> products;
}
