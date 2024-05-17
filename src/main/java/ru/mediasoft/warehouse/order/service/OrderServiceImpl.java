package ru.mediasoft.warehouse.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mediasoft.warehouse.customer.model.Customer;
import ru.mediasoft.warehouse.customer.repository.CustomerRepository;
import ru.mediasoft.warehouse.exception.CustomerAccessException;
import ru.mediasoft.warehouse.exception.OrderIsNotValidException;
import ru.mediasoft.warehouse.exception.OrderStatusValidException;
import ru.mediasoft.warehouse.order.dto.OrderDtoIn;
import ru.mediasoft.warehouse.order.dto.OrderDtoOut;
import ru.mediasoft.warehouse.order.model.Order;
import ru.mediasoft.warehouse.order.model.OrderStatus;
import ru.mediasoft.warehouse.order.model.OrderedProduct;
import ru.mediasoft.warehouse.order.model.OrderedProductId;
import ru.mediasoft.warehouse.order.repository.OrderRepository;
import ru.mediasoft.warehouse.order.repository.OrderedProductRepository;
import ru.mediasoft.warehouse.order.util.OrderMapper;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderIn;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderOut;
import ru.mediasoft.warehouse.product.dto.ProductDtoFotUpdate;
import ru.mediasoft.warehouse.product.model.Product;
import ru.mediasoft.warehouse.product.repository.ProductRepository;
import ru.mediasoft.warehouse.product.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final ProductService productService;

    @Transactional
    @Override
    public UUID create(long customerId, OrderDtoIn dto) {
        Customer customer = getCustomer(customerId);       // проверка на наличие пользователя в бд
        Map<UUID, Integer> orderMap = dto.getProducts().stream()
                .collect(Collectors.toMap(ProductDtoForOrderIn::getId, ProductDtoForOrderIn::getQuantity));
        Order order = Order.builder()
                .deliveryAddress(dto.getDeliveryAddress())
                .status(OrderStatus.CREATED)
                .customer(customer)
                .build();
        List<OrderedProduct> orderedProductList = makingOrderedProduct(order, orderMap);
        order.setOrderedProducts(orderedProductList);
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    @Override
    public UUID update(long customerId, UUID orderId, List<ProductDtoForOrderIn> products) {
        Order order = getOrder(orderId);
        validationForAdministration(order, customerId);     // проверка прав доступа
        Map<UUID, Integer> orderMap = products.stream()
                .collect(Collectors.toMap(ProductDtoForOrderIn::getId, ProductDtoForOrderIn::getQuantity));
        updateOrderedProducts(order, orderMap);     // обновляем данные в ORDERED_PRODUCT
        orderRepository.save(order);
        return orderId;
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDtoOut getByOrderId(long customerId, UUID orderId) {
        Order order = getOrder(orderId);
        validationForAdministration(order, customerId);     // проверка прав доступа
        List<ProductDtoForOrderOut> list = orderedProductRepository.findAllWhereOrderId(orderId);
        return OrderMapper.toOrderInfoDto(list, orderId);
    }

    @Transactional
    @Override
    public void deleteByOrderId(long customerId, UUID orderId) {
        Order order = getOrder(orderId);
        validationForAdministration(order, customerId);     // проверка прав доступа

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        rollbackProducts(order);     // возврат продуктов на склад
    }

    private void rollbackProducts(Order order) {
        List<OrderedProduct> orderedProducts = order.getOrderedProducts();
        for (OrderedProduct orderedProduct : orderedProducts) {
            ProductDtoFotUpdate productToSave = ProductDtoFotUpdate.builder()
                    .quantity(orderedProduct.getQuantity() + orderedProduct.getProduct().getQuantity())
                    .build();
            productService.update(orderedProduct.getProduct().getId(), productToSave);
            orderedProduct.setStatus(OrderStatus.CANCELLED);
            orderedProductRepository.save(orderedProduct);
        }
    }

    @Override
    public void changeOrderStatus(UUID orderId, OrderStatus status) {
        Order order = getOrder(orderId);
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public void confirm(long customerId, UUID orderId) {
    }

    private Customer getCustomer(long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден в базе данных. id=" + customerId));
    }

    private void validationForAdministration(Order order, Long customerId) {
        if (!order.getCustomer().getId().equals(customerId)) {
            throw new CustomerAccessException("У пользователя нет права на редактирование заказа");
        }
        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            throw new OrderStatusValidException("Редактирование заказа запрещено");
        }
    }

    private Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказа с данным Id нет в базе данных: " + orderId));
    }

    private List<OrderedProduct> makingOrderedProduct(Order order, Map<UUID, Integer> orderMap) {
        return productRepository.findAllById(orderMap.keySet()).stream()
                .peek(product -> {
                    productValidator(product, orderMap);   // проверка на наличие и доступность товаров
                    quantityUpdate(product, orderMap);     // изменение количества товаров
                })
                .map(product -> OrderedProduct.builder()
                        .order(order)
                        .product(product)
                        .status(OrderStatus.CREATED)
                        .price(product.getPrice())
                        .quantity(orderMap.get(product.getId()))
                        .id(OrderedProductId.builder()
                                .orderId(order.getId())
                                .productId(product.getId())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    private void quantityUpdate(Product product, Map<UUID, Integer> orderMap) {
        int newQuantity = product.getQuantity() - orderMap.get(product.getId());
        product.setQuantity(newQuantity);
    }

    private void productValidator(Product product, Map<UUID, Integer> orders) {
        if (!product.isAvailable()) {
            throw new OrderIsNotValidException("Товар с id= " + product.getId() + " не доступен на складе");
        }
        if (product.getQuantity() < orders.get(product.getId())) {
            throw new OrderIsNotValidException("Количество товара с id= " + product.getId() + " недостаточно для оформления заказа");
        }
    }

    private void updateOrderedProducts(Order order, Map<UUID, Integer> orderMap) {
        Map<UUID, OrderedProduct> oldOrderedProducts = order.getOrderedProducts().stream()
                .collect(Collectors.toMap(
                        orderedProduct -> orderedProduct.getProduct().getId(),
                        orderedProduct -> orderedProduct));

        productRepository.findAllById(orderMap.keySet()).forEach(product -> {
            productValidator(product, orderMap); // проверка на наличие и доступность товаров
            quantityUpdate(product, orderMap);   // изменение количества товаров

            UUID productId = product.getId();
            int newQuantity = orderMap.get(productId);
            OrderedProduct orderedProduct = oldOrderedProducts.get(productId);

            if (orderedProduct != null) {
                newQuantity += orderedProduct.getQuantity();
                orderedProduct.setQuantity(newQuantity);      // обновление существующего orderedProduct
            } else {
                orderedProduct = OrderedProduct.builder()
                        .id(OrderedProductId.builder()
                                .productId(productId)
                                .orderId(order.getId())
                                .build())
                        .product(product)
                        .order(order)
                        .price(product.getPrice())
                        .status(OrderStatus.CREATED)
                        .quantity(newQuantity)
                        .build();
                order.getOrderedProducts().add(orderedProduct);  // добавление нового orderedProduct
            }
        });
    }

}
