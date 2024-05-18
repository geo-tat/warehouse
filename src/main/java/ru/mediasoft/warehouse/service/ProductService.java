package ru.mediasoft.warehouse.service;

import org.springframework.data.domain.Pageable;
import ru.mediasoft.warehouse.dto.ProductDtoForUpdate;
import ru.mediasoft.warehouse.dto.ProductDtoIn;
import ru.mediasoft.warehouse.dto.ProductDtoInfo;
import ru.mediasoft.warehouse.dto.ProductDtoOut;
import ru.mediasoft.warehouse.search.criteria.SearchCriteria;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDtoInfo create(ProductDtoIn dto);

    ProductDtoInfo update(UUID id, ProductDtoForUpdate dto);

    ProductDtoOut getById(UUID id);

    Collection<ProductDtoOut> getAll(Pageable pageable);

    void deleteById(UUID id);

    Collection<ProductDtoOut> multiCriteriaSearch(List<SearchCriteria<?>> criteriaList, Pageable pageable);
}
