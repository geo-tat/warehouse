package ru.mediasoft.warehouse.product.service;

import org.springframework.data.domain.Pageable;
import ru.mediasoft.warehouse.product.dto.ProductDtoForUpdate;
import ru.mediasoft.warehouse.product.dto.ProductDtoIn;
import ru.mediasoft.warehouse.product.dto.ProductDtoOut;
import ru.mediasoft.warehouse.product.search.criteria.SearchCriteria;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDtoOut create(ProductDtoIn dto);

    ProductDtoOut update(UUID id, ProductDtoForUpdate dto);

    ProductDtoOut getById(UUID id);

    Collection<ProductDtoOut> getAll(Pageable pageable);

    void deleteById(UUID id);

    Collection<ProductDtoOut> multiCriteriaSearch(List<SearchCriteria<?>> criteriaList, Pageable pageable);
}
