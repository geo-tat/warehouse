package ru.mediasoft.warehouse.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mediasoft.warehouse.customer.model.Customer;
import ru.mediasoft.warehouse.customer.repository.CustomerRepository;
import ru.mediasoft.warehouse.exception.CustomerAccessException;
import ru.mediasoft.warehouse.exception.OrderStatusValidException;
import ru.mediasoft.warehouse.order.dto.OrderDtoIn;
import ru.mediasoft.warehouse.order.dto.OrderDtoOut;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderOut;
import ru.mediasoft.warehouse.order.model.Order;
import ru.mediasoft.warehouse.order.model.OrderStatus;
import ru.mediasoft.warehouse.order.model.OrderedProduct;
import ru.mediasoft.warehouse.order.model.OrderedProductId;
import ru.mediasoft.warehouse.order.repository.OrderRepository;
import ru.mediasoft.warehouse.order.repository.OrderedProductRepository;
import ru.mediasoft.warehouse.order.util.OrderMapper;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderIn;
import ru.mediasoft.warehouse.product.dto.ProductDtoFotUpdate;
import ru.mediasoft.warehouse.product.model.Product;
import ru.mediasoft.warehouse.product.repository.ProductRepository;
import ru.mediasoft.warehouse.product.service.ProductService;

import java.util.ArrayList;
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
        List<Product> productsFromWarehouse = availability(orderMap);    // проверка на наличие товара на складе и его количества
        List<Product> productToSave = quantityUpdate(productsFromWarehouse, orderMap); // перерасчет количества товара
        Order order = orderRepository.save(Order.builder()
                .deliveryAddress(dto.getDeliveryAddress())
                .status(OrderStatus.CREATED)
                .customer(customer)
                .build());

        holdingOrderedProducts(order, productToSave, orderMap);  // заполнили таблицу ORDERED_PRODUCT
        return order.getId();
    }

    @Transactional
    @Override
    public UUID update(long customerId, UUID orderId, List<ProductDtoForOrderIn> products) {
        Customer customer = getCustomer(customerId);
        Order order = getOrder(orderId);
        validationForAdministration(order, customerId);     // проверка прав доступа

        Map<UUID, Integer> orderMap = products.stream()
                .collect(Collectors.toMap(ProductDtoForOrderIn::getId, ProductDtoForOrderIn::getQuantity));
        List<Product> productsFromWarehouse = availability(orderMap);  // проверка на наличие товара на складе и его количества
        List<Product> productToSave = quantityUpdate(productsFromWarehouse, orderMap); // перерасчет количества товара
        updateOrderedProducts(order, productToSave, orderMap);     // обновляем данные в ORDERED_PRODUCT
        return orderId;
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDtoOut getByOrderId(long customerId, UUID orderId) {
        Customer customer = getCustomer(customerId);
        Order order = getOrder(orderId);
        validationForAdministration(order, customerId);     // проверка прав доступа
        List<ProductDtoForOrderOut> list = orderedProductRepository.findAllWhereOrderId(orderId);
        return OrderMapper.toOrderInfoDto(list, orderId);
    }

    @Transactional
    @Override
    public void deleteByOrderId(long customerId, UUID orderId) {
        Customer customer = getCustomer(customerId);
        Order order = getOrder(orderId);
        validationForAdministration(order, customerId);     // проверка прав доступа

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        rollbackProducts(orderId);     // возврат продуктов на склад
    }

    private void rollbackProducts(UUID orderId) {
        List<OrderedProduct> orderedProducts = orderedProductRepository.findAllByOrderId(orderId);
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
    public String changeOrderStatus(UUID orderId, OrderStatus status) {
        Order order = getOrder(orderId);
        order.setStatus(status);
        orderRepository.save(order);
        return "";
    }

    @Override
    public String confirm(long customerId, UUID orderId) {
        return "";
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

    private void holdingOrderedProducts(Order order, List<Product> productToSave, Map<UUID, Integer> orderMap) {
        for (Product product : productToSave) {
            orderedProductRepository.save(OrderedProduct.builder()
                    .id(OrderedProductId.builder()
                            .productId(product.getId())
                            .orderId(order.getId()).build())
                    .product(product)
                    .order(order)
                    .quantity(orderMap.get(product.getId()))
                    .price(product.getPrice())
                    .status(OrderStatus.CREATED)
                    .build());
        }
    }

    private List<Product> quantityUpdate(List<Product> products, Map<UUID, Integer> orderMap) {
        int newQuantity = 0;
        for (Product product : products) {
            newQuantity = product.getQuantity() - orderMap.get(product.getId());
            product.setQuantity(newQuantity);
        }
        return products;
    }

    private List<Product> availability(Map<UUID, Integer> orders) {
        List<UUID> ids = orders.keySet().stream().toList();
        List<Integer> quantities = orders.values().stream().toList();
        List<Product> products = productRepository.findAllByIdInAndIsAvailableTrue(ids);
        List<Product> finalProductList = new ArrayList<>();
        for (Product product : products) {
            if (orders.get(product.getId()) <= product.getQuantity())
                finalProductList.add(product);
        }
        return finalProductList;
    }

    private void updateOrderedProducts(Order order, List<Product> productToSave, Map<UUID, Integer> orderMap) {
        List<OrderedProduct> oldOrderedProducts = orderedProductRepository.findAllByOrderId(order.getId());
        Map<UUID, Integer> oldProductsHashMap = oldOrderedProducts.stream()
                .collect(Collectors.toMap(
                        orderedProduct -> orderedProduct.getProduct().getId(),
                        OrderedProduct::getQuantity));

        for (Product product : productToSave) {
            int newQuantity = orderMap.get(product.getId()) + oldProductsHashMap.getOrDefault(product.getId(), 0);
            OrderedProduct op = OrderedProduct.builder()
                    .id(OrderedProductId.builder()
                            .productId(product.getId())
                            .orderId(order.getId())
                            .build())
                    .product(product)
                    .quantity(newQuantity)
                    .price(product.getPrice())
                    .status(order.getStatus())
                    .order(order)
                    .build();
            orderedProductRepository.save(op);
        }
    }
}
