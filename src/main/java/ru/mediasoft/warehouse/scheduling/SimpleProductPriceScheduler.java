package ru.mediasoft.warehouse.scheduling;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mediasoft.warehouse.annotation.MeasureTime;
import ru.mediasoft.warehouse.model.Product;
import ru.mediasoft.warehouse.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Collection;


@Component
@Profile("!local")
@ConditionalOnProperty(name = "app.scheduling.enabled")
@ConditionalOnMissingBean(name = "optimizedProductPriceScheduler")
@RequiredArgsConstructor
public class SimpleProductPriceScheduler {

    private final ProductRepository repository;
@Value("${app.scheduling.priceIncreasePercentage}")
    private BigDecimal percentage;

    @Transactional
    @Scheduled(fixedRateString = "${app.scheduling.period}")
    @MeasureTime
    public void increasePrice() {
        System.out.println("START SIMPLE SCHEDULER!");
        final Collection<Product> products = repository.findAll();
        products.forEach(product -> product.setPrice(increase(product.getPrice(), percentage)));
        repository.saveAll(products);
        System.out.println("END SIMPLE SCHEDULER!");
    }

    private BigDecimal increase(BigDecimal oldPrice, BigDecimal percentage) {
        BigDecimal increaseAmount = oldPrice.multiply(percentage.divide(new BigDecimal(100)));
        return oldPrice.add(increaseAmount);
    }
}















