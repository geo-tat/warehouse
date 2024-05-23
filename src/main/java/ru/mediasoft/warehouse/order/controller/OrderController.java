package ru.mediasoft.warehouse.order.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mediasoft.warehouse.order.dto.OrderDtoIn;
import ru.mediasoft.warehouse.order.dto.OrderDtoOut;
import ru.mediasoft.warehouse.order.dto.OrderInfo;
import ru.mediasoft.warehouse.order.model.OrderStatus;
import ru.mediasoft.warehouse.order.service.OrderService;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderIn;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "Оформление заказов")
public class OrderController {

    private final OrderService service;

    @PostMapping
    UUID create(@RequestHeader("customerId") long customerId,
                @RequestBody OrderDtoIn dto) {

        return service.create(customerId, dto);
    }

    @PatchMapping("/{orderId}")
    UUID update(@RequestHeader("customerId") long customerId,
                @PathVariable UUID orderId,
                @RequestBody List<ProductDtoForOrderIn> products) {

        return service.update(customerId, orderId, products);
    }

    @GetMapping("/{orderId}")
    OrderDtoOut getByOrderId(@RequestHeader("customerId") long customerId,
                             @PathVariable UUID orderId) {

        return service.getByOrderId(customerId, orderId);
    }

    @DeleteMapping("/{orderId}")
    void deleteByOrderId(@RequestHeader("customerId") long customerId,
                         @PathVariable UUID orderId) {

        service.deleteByOrderId(customerId, orderId);
    }

    @PostMapping("/{orderId}/confirm")
    void confirm(@RequestHeader("customerId") long customerId,
                 @PathVariable UUID orderId) {
        service.confirm(customerId, orderId);
    }

    @PatchMapping("/{orderId}/status ")
    void changeOrderStatus(@PathVariable UUID orderId,
                           @RequestHeader("status") OrderStatus status) {

        service.changeOrderStatus(orderId, status);
    }

    @GetMapping("/info")
    public Map<UUID, List<OrderInfo>> getOrderInfoByProduct() {
        return service.getOrderInfoByProduct();
    }
}

