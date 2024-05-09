package ru.mediasoft.warehouse.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mediasoft.warehouse.order.dto.OrderDtoIn;
import ru.mediasoft.warehouse.order.dto.OrderDtoOut;
import ru.mediasoft.warehouse.order.service.OrderService;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderIn;

import java.util.List;
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
    String confirm(@RequestHeader("customerId") long customerId,
                   @PathVariable UUID orderId) {
        return service.confirm(customerId, orderId);
    }

    @PatchMapping("/{orderId}/status ")
    String changeOrderStatus(@PathVariable UUID orderId,
                             @RequestHeader("status") OrderStatus status) {

        return service.changeOrderStatus(orderId, status);
    }

}

