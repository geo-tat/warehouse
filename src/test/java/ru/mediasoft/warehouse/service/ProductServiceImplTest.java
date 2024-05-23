package ru.mediasoft.warehouse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.mediasoft.warehouse.product.dto.ProductDtoFotUpdate;
import ru.mediasoft.warehouse.product.dto.ProductDtoIn;
import ru.mediasoft.warehouse.product.dto.ProductDtoOut;
import ru.mediasoft.warehouse.product.model.CategoryType;
import ru.mediasoft.warehouse.product.model.Product;
import ru.mediasoft.warehouse.product.repository.ProductRepository;
import ru.mediasoft.warehouse.product.service.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDtoIn dtoIn;
    private ProductDtoFotUpdate dtoUpdate;
    Pageable pageable = PageRequest.of(0, 10);
    @BeforeEach
    public void setUp() {
        product = Product.builder()
                .id(UUID.randomUUID())
                .name("Name")
                .sku("SKU")
                .description("Description")
                .category(CategoryType.CATEGORY)
                .price(BigDecimal.valueOf(10.0))
                .quantity(100)
                .build();
        dtoIn = ProductDtoIn.builder()
                .name("Name")
                .sku("SKU")
                .description("Description")
                .category(CategoryType.CATEGORY)
                .price(BigDecimal.valueOf(10.0))
                .quantity(100)
                .build();
        dtoUpdate = ProductDtoFotUpdate.builder()
                .name("NewName")
                .sku("NewSKU")
                .description("NewDescription")
                .category(CategoryType.CATEGORY)
                .price(BigDecimal.valueOf(110.0))
                .quantity(1100)
                .build();
    }

    @Test
    public void testCreateProduct() {
        when(repository.save(any())).thenReturn(product);

        ProductDtoOut createdProduct = productService.create(dtoIn);

        assertNotNull(createdProduct);
        assertEquals(product.getId(), createdProduct.getId());
        assertEquals(product.getSku(), createdProduct.getSku());
        assertEquals(product.getName(), createdProduct.getName());
    }

    @Test
    public void testUpdateProduct() {
        when(repository.findById(product.getId())).thenReturn(Optional.of(product));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ProductDtoOut updatedProduct = productService.update(product.getId(), dtoUpdate);

        assertNotNull(updatedProduct);
        assertEquals(dtoUpdate.getSku(), updatedProduct.getSku());
        assertEquals(dtoUpdate.getName(), updatedProduct.getName());

    }

    @Test
    public void testGetProductById() {
        when(repository.findById(product.getId())).thenReturn(Optional.of(product));

        ProductDtoOut retrievedProduct = productService.getById(product.getId());

        assertNotNull(retrievedProduct);
        assertEquals(product.getId(), retrievedProduct.getId());
        assertEquals(product.getSku(), retrievedProduct.getSku());
    }

    @Test
    public void testGetAllProducts() {
        when(repository.findAll()).thenReturn(Collections.singletonList(product));

        assertEquals(1, productService.getAll(pageable).size());

    }

    @Test
    public void testDeleteProductById() {
        when(repository.findById(product.getId())).thenReturn(Optional.of(product));

        productService.deleteById(product.getId());

        verify(repository, times(1)).deleteById(product.getId());
    }

    @Test
    public void testCreateProductWithDuplicateSku() {
        when(repository.save(any())).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class, () -> {
            productService.create(dtoIn);
        });
    }
}
