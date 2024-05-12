package ru.mediasoft.warehouse.order.model;

import jakarta.persistence.*;
import lombok.*;
import ru.mediasoft.warehouse.product.model.Product;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ordered_product")
public class OrderedProduct {

    @EmbeddedId
    private OrderedProductId id;

    @ManyToOne
    @MapsId("orderId")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    private Product product;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    private int quantity;

    private BigDecimal price;
}
