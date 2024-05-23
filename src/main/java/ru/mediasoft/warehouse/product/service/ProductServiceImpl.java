package ru.mediasoft.warehouse.product.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mediasoft.warehouse.product.dto.ProductDtoForUpdate;
import ru.mediasoft.warehouse.product.dto.ProductDtoIn;
import ru.mediasoft.warehouse.product.dto.ProductDtoOut;
import ru.mediasoft.warehouse.product.model.Product;
import ru.mediasoft.warehouse.product.repository.ProductRepository;
import ru.mediasoft.warehouse.product.search.criteria.SearchCriteria;
import ru.mediasoft.warehouse.product.util.ProductMapper;
import ru.mediasoft.warehouse.product.util.ProductSpecification;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    ProductRepository repository;

    @Override
    public ProductDtoOut create(ProductDtoIn dto) {
        Product productToSave = ProductMapper.toEntity(dto);
        UUID id = UUID.randomUUID();
        productToSave.setId(id);
        return ProductMapper.toOut(repository.save(productToSave));
    }

    @Override
    public ProductDtoOut update(UUID id, ProductDtoForUpdate dto) {
        Product productToUpdate = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Товар на складе не найден."));
        Product productToSave = updateProduct(dto, productToUpdate);
        return ProductMapper.toOut(repository.save(productToSave));
    }

    private Product updateProduct(ProductDtoForUpdate dto, Product productToUpdate) {
        if (dto.getCategory() != null)
            productToUpdate.setCategory(dto.getCategory());
        if (dto.getName() != null)
            productToUpdate.setName(dto.getName());
        if (dto.getPrice() != null)
            productToUpdate.setPrice(dto.getPrice());
        if (dto.getDescription() != null)
            productToUpdate.setDescription(dto.getDescription());
        if (dto.getSku() != null)
            productToUpdate.setSku(dto.getSku());
        if (dto.getQuantity() != null && dto.getQuantity() != productToUpdate.getQuantity()) {
            productToUpdate.setQuantity(dto.getQuantity());
            productToUpdate.setUpdatedQuantity(LocalDateTime.now());
        }
        return productToUpdate;
    }

    @Override
    public ProductDtoOut getById(UUID id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Товар на складе не найден."));
        return ProductMapper.toOut(product);
    }

    @Override
    public Collection<ProductDtoOut> getAll(Pageable pageable) {
        Page<Product> list = repository.findAll(pageable);
        return list.stream()
                .map(ProductMapper::toOut)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Товар на складе не найден."));
        repository.deleteById(id);
    }

    @Override
    public Collection<ProductDtoOut> multiCriteriaSearch(List<SearchCriteria<?>> criteriaList, Pageable pageable) {
        final ProductSpecification specification = new ProductSpecification(criteriaList);
        Page<Product> page = repository.findAll(specification, pageable);

        return page.getContent().stream()
                .map(ProductMapper::toOut)
                .collect(Collectors.toList());
    }
}
