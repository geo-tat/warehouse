package ru.mediasoft.warehouse.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mediasoft.warehouse.customer.model.Customer;
import ru.mediasoft.warehouse.customer.repository.CustomerRepository;
import ru.mediasoft.warehouse.exception.CustomerAccessException;
import ru.mediasoft.warehouse.exception.OrderStatusValidException;
import ru.mediasoft.warehouse.exception.QuantityIsNotValidException;
import ru.mediasoft.warehouse.order.OrderMapper;
import ru.mediasoft.warehouse.order.OrderStatus;
import ru.mediasoft.warehouse.order.dto.OrderDtoIn;
import ru.mediasoft.warehouse.order.dto.OrderDtoOut;
import ru.mediasoft.warehouse.order.dto.OrderProductDtoInfo;
import ru.mediasoft.warehouse.order.model.Order;
import ru.mediasoft.warehouse.order.model.OrderedProduct;
import ru.mediasoft.warehouse.order.model.OrderedProductId;
import ru.mediasoft.warehouse.order.repository.OrderRepository;
import ru.mediasoft.warehouse.order.repository.OrderedProductRepository;
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
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final ProductService productService;

    @Transactional
    @Override
    public UUID create(long customerId, OrderDtoIn dto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден в базе данных. id=" + customerId));

        List<Product> productsForOrder = getProductsForOrderFromWarehouse(dto.getProducts());
        List<Product> backToWarehouseProducts = changingQuantity(dto.getProducts(), List.copyOf(productsForOrder));

        Order orderToSave = Order.builder()
                .status(OrderStatus.CREATED)
                .deliveryAddress(dto.getDeliveryAddress())
                .customer(customer)
                .build();
        // сохранили заказ в бд
        Order savedOrder = orderRepository.save(orderToSave);
        Map<UUID, Integer> order = dto.getProducts().stream()
                .collect(Collectors.toMap(ProductDtoForOrderIn::getId, ProductDtoForOrderIn::getQuantity));
        for (Product product : backToWarehouseProducts) {
            //сохраняем в таблицу связи заказа и товара, заморозили цену
            OrderedProduct o = OrderedProduct.builder()
                    .id(OrderedProductId.builder()
                            .productId(product.getId())
                            .orderId(orderToSave.getId())
                            .build())
                    .product(product)
                    .quantity(order.get(product.getId()))
                    .price(product.getPrice())
                    .status(orderToSave.getStatus())
                    .order(savedOrder)
                    .build();
            orderedProductRepository.save(o);
        }
        productRepository.saveAll(backToWarehouseProducts);    // обновили товары в таблице product
        return orderToSave.getId();
    }

    private List<Product> getProductsForOrderFromWarehouse(List<ProductDtoForOrderIn> products) {
        List<UUID> ids = products.stream()
                .map(ProductDtoForOrderIn::getId)
                .toList();
        return productRepository.findAllByIdInAndIsAvailableTrue(ids);
    }

    @Transactional
    @Override
    public UUID update(long customerId, UUID orderId, List<ProductDtoForOrderIn> products) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказа с данным Id нет в базе данных: " + orderId));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с данным Id нет в базе данных: " + customerId));

        validationForUpdate(order, customerId);
        List<Product> productsForOrder = getProductsForOrderFromWarehouse(products);

        // возвращает список товаров с измененным количеством
        List<Product> backToWarehouseProducts = changingQuantity(products, productsForOrder);
        List<OrderedProduct> oldOrderedProducts = orderedProductRepository.findAllByOrderId(orderId);


        Map<UUID, Integer> newProductsHasMap = products.stream()
                .collect(Collectors.toMap(ProductDtoForOrderIn::getId, ProductDtoForOrderIn::getQuantity));
        Map<UUID, Integer> oldProductsHashMap = oldOrderedProducts.stream()
                .collect(Collectors.toMap(
                        orderedProduct -> orderedProduct.getProduct().getId(),
                        OrderedProduct::getQuantity));

        for (Product product : backToWarehouseProducts) {
            int newQuantity = newProductsHasMap.get(product.getId()) + oldProductsHashMap.getOrDefault(product.getId(), 0);
            OrderedProduct o = OrderedProduct.builder()
                    .id(OrderedProductId.builder()
                            .productId(product.getId())
                            .orderId(orderId)
                            .build())
                    .product(product)
                    .quantity(newQuantity)
                    .price(product.getPrice())
                    .status(order.getStatus())
                    .order(order)
                    .build();
            orderedProductRepository.save(o);
        }
        productRepository.saveAll(backToWarehouseProducts);

        return null;

    }

    private List<Product> changingQuantity(List<ProductDtoForOrderIn> products, List<Product> listFromWarehouse) {
        List<Product> productsBackToWarehouse = new ArrayList<>();
        Map<UUID, Integer> order = products.stream()
                .collect(Collectors.toMap(ProductDtoForOrderIn::getId, ProductDtoForOrderIn::getQuantity));
        for (Product product : listFromWarehouse) {
            int newQ = product.getQuantity() - order.get(product.getId());
            if (newQ >= 0) {
                product.setQuantity(newQ);
                productsBackToWarehouse.add(product);
            } else {
                throw new QuantityIsNotValidException("На складе не достаточное количество товара с id= " + product.getId());
            }
        }
        return productsBackToWarehouse;
    }


    private void validationForUpdate(Order order, Long customerId) {
        if (!order.getCustomer().getId().equals(customerId)) {
            throw new CustomerAccessException("У пользователя нет права на редактирование заказа");
        }
        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            throw new OrderStatusValidException("Редактирование заказа запрещено");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDtoOut getByOrderId(long customerId, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказа с данным Id нет в базе данных: " + orderId));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с данным Id нет в базе данных: " + customerId));
        List<OrderProductDtoInfo> list = orderedProductRepository.findAllWhereOrderId(orderId);
        return OrderMapper.toOrderInfoDto(list, orderId);
    }

    @Transactional
    @Override
    public void deleteByOrderId(long customerId, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказа с данным Id нет в базе данных: " + orderId));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с данным Id нет в базе данных: " + customerId));
        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            throw new OrderStatusValidException("Нельзя отменить заказ");
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

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
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказа с данным Id нет в базе данных: " + orderId));
        order.setStatus(status);
        orderRepository.save(order);
        return "";
    }

    @Override
    public String confirm(long customerId, UUID orderId) {
        return "";
    }
}
