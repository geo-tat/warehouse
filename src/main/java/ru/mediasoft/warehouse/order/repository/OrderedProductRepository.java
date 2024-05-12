package ru.mediasoft.warehouse.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderOut;
import ru.mediasoft.warehouse.order.model.OrderedProduct;
import ru.mediasoft.warehouse.order.model.OrderedProductId;

import java.util.List;
import java.util.UUID;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, OrderedProductId> {

    List<OrderedProduct> findAllByOrderId(UUID orderId);

    @Query("SELECT new ru.mediasoft.warehouse.product.dto.ProductDtoForOrderOut(o.product.id, o.product.name, o.quantity, o.price) FROM OrderedProduct o WHERE o.order.id = :orderId")
    List<ProductDtoForOrderOut> findAllWhereOrderId(UUID orderId);
}
