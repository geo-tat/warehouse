package ru.mediasoft.warehouse.service;

import org.springframework.data.domain.Pageable;
import ru.mediasoft.warehouse.dto.ProductDtoFotUpdate;
import ru.mediasoft.warehouse.dto.ProductDtoIn;
import ru.mediasoft.warehouse.dto.ProductDtoOut;
import ru.mediasoft.warehouse.search.criteria.SearchCriteria;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDtoOut create(ProductDtoIn dto);

    ProductDtoOut update(UUID id, ProductDtoFotUpdate dto);

    ProductDtoOut getById(UUID id);

    Collection<ProductDtoOut> getAll(Pageable pageable);

    void deleteById(UUID id);

    Collection<ProductDtoOut> multiCriteriaSearch(List<SearchCriteria<?>> criteriaList, Pageable pageable);
}
