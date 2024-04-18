package ru.mediasoft.warehouse.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.mediasoft.warehouse.model.Product;
import ru.mediasoft.warehouse.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Component
@ConditionalOnProperty(name = "app.util.seeder.enabled")
public class DatabaseSeeder {

    private final ProductRepository productRepository;

    @Autowired
    public DatabaseSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void seedDatabase() {
        Instant start = Instant.now();
        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setId(UUID.randomUUID());
            product.setSku(generateRandomSku());
            product.setName("товар" + i);
            product.setPrice(BigDecimal.valueOf(100));
            product.setQuantity(10);
            product.setCategory("Нечто");
            productRepository.save(product);
        }
        Instant end = Instant.now();
        Duration duration = Duration.between(start,end);
        System.out.println("Время записи в секундах: " + duration.toSeconds());
    }

    private String generateRandomSku() {

        return "SKU-" + UUID.randomUUID().toString().replace("-", "");
    }
}
