package ru.mediasoft.warehouse.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.mediasoft.warehouse.repository.ProductRepository;

@TestConfiguration
public class TestConfig {

    @Bean
    public ProductServiceImpl productService(ProductRepository productRepository) {
        return new ProductServiceImpl(productRepository);
    }
}