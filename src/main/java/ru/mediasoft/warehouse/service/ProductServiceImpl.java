package ru.mediasoft.warehouse.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.mediasoft.warehouse.dto.ProductDtoFotUpdate;
import ru.mediasoft.warehouse.dto.ProductDtoIn;
import ru.mediasoft.warehouse.dto.ProductDtoOut;
import ru.mediasoft.warehouse.model.Product;
import ru.mediasoft.warehouse.repository.ProductRepository;
import ru.mediasoft.warehouse.search.criteria.SearchCriteria;
import ru.mediasoft.warehouse.search.strategy.PredicateStrategy;
import ru.mediasoft.warehouse.util.ProductMapper;
import ru.mediasoft.warehouse.util.SortingMapper;

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
        return ProductMapper.toOut(repository.save(productToSave));
    }

    @Override
    public ProductDtoOut update(UUID id, ProductDtoFotUpdate dto) {
        Product productToUpdate = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Товар на складе не найден."));
        Product productToSave = updateProduct(dto, productToUpdate);
        return ProductMapper.toOut(repository.save(productToSave));
    }

    private Product updateProduct(ProductDtoFotUpdate dto, Product productToUpdate) {
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
    public Collection<ProductDtoOut> getAll() {
        Collection<Product> list = repository.findAll();
        return list.stream()
                .map(ProductMapper::toOut)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public void deleteById(UUID id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Товар на складе не найден."));
        repository.deleteById(id);
    }


    @Override
    public Collection<ProductDtoOut> multiCriteriaSearch(List<SearchCriteria<?>> criteriaList, int from, int size, String sort) {
        Specification<Product> specification = buildSpecification(criteriaList);
        Pageable pageable = PageRequest.of(from, size, SortingMapper.sorting(sort));

        Page<Product> page = repository.findAll(specification, pageable);

        return page.getContent().stream()
                .map(ProductMapper::toOut)
                .collect(Collectors.toList());
    }

    private Specification<Product> buildSpecification(List<SearchCriteria<?>> criteriaList) {
        return (root, query, cb) -> {
            Predicate finalPredicate = cb.conjunction();

            for (SearchCriteria<?> criteria : criteriaList) {
                Predicate predicate = createPredicate(criteria, root, cb);
                finalPredicate = cb.and(finalPredicate, predicate);
            }

            return finalPredicate;
        };
    }

    private <T> Predicate createPredicate(SearchCriteria<T> criteria, Root<Product> root, CriteriaBuilder cb) {
        Expression<T> expression = root.get(criteria.getField());
        PredicateStrategy<T> strategy = criteria.getStrategy();
        T value = criteria.getValue();
        String operation = criteria.getOperation().name();

        return switch (operation) {
            case "EQUAL" -> strategy.getEqPattern(expression, value, cb);
            case "LIKE" -> strategy.getLikePattern(expression, value, cb);
            case "GREATER_THAN_OR_EQ" -> strategy.getLefLimitPattern(expression, value, cb);
            case "LESS_THAN_OR_EQ" -> strategy.getRightLimitPattern(expression, value, cb);
            default -> throw new IllegalArgumentException("Ошибка операции: " + operation);
        };
    }
}
