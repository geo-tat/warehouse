package ru.mediasoft.warehouse.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import ru.mediasoft.warehouse.dto.ProductDtoOut;
import ru.mediasoft.warehouse.model.CategoryType;
import ru.mediasoft.warehouse.model.Product;
import ru.mediasoft.warehouse.repository.ProductRepository;
import ru.mediasoft.warehouse.search.criteria.BigDecimalSearchCriteria;
import ru.mediasoft.warehouse.search.criteria.StringSearchCriteria;
import ru.mediasoft.warehouse.search.enums.OperationType;
import ru.mediasoft.warehouse.service.currency.ExchangeRateProvider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@DataJpaTest
@ActiveProfiles("local")
public class ProductServiceIntegrationalTest {
    @Autowired
    ProductRepository repositoryTest;
    @Autowired
    ExchangeRateProvider exchangeRateProvider;

    ProductService service;

    Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    void setUp() {
        service = new ProductServiceImpl(repositoryTest, exchangeRateProvider);

        Product product = Product.builder()
                .name("Name")
                .sku("SKU")
                .description("Description")
                .category(CategoryType.CATEGORY)
                .price(BigDecimal.valueOf(10.0))
                .quantity(100)
                .build();

        Product product2 = Product.builder()
                .name("Name2")
                .sku("SKU2")
                .description("Description2")
                .category(CategoryType.CATEGORY)
                .price(BigDecimal.valueOf(50.0))
                .quantity(100)
                .build();
        Product product3 = Product.builder()
                .name("Name3")
                .sku("SKU3")
                .description("Description3")
                .category(CategoryType.CATEGORY)
                .price(BigDecimal.valueOf(100.0))
                .quantity(150)
                .build();
        repositoryTest.save(product);
        repositoryTest.save(product2);
        repositoryTest.save(product3);
    }

    @Test
    public void testSearchByPrice() throws Exception {
        List<StringSearchCriteria> criteriaList = new ArrayList<>();
        BigDecimalSearchCriteria criteria = BigDecimalSearchCriteria.builder()
                .field("price")
                .value(BigDecimal.valueOf(40))
                .operation(OperationType.GREATER_THAN_OR_EQ)
                .build();

        Collection<ProductDtoOut> testList = service.multiCriteriaSearch(List.of(criteria), pageable);

        Assertions.assertEquals(2, testList.size());
    }

    @Test
    public void testSearchByName() throws Exception {
        StringSearchCriteria criteria = StringSearchCriteria.builder()
                .field("name")
                .value("Name3")
                .operation(OperationType.EQUAL)
                .build();

        Collection<ProductDtoOut> testList = service.multiCriteriaSearch(List.of(criteria), pageable);

        Assertions.assertEquals(1, testList.size());
    }
}
