package ru.mediasoft.warehouse.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mediasoft.warehouse.order.model.Order;
import ru.mediasoft.warehouse.order.model.OrderStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByStatusIn(List<OrderStatus> statuses);
}
