package ru.mediasoft.warehouse.service;

import ru.mediasoft.warehouse.dto.ProductDtoFotUpdate;
import ru.mediasoft.warehouse.dto.ProductDtoIn;
import ru.mediasoft.warehouse.dto.ProductDtoOut;

import java.util.Collection;
import java.util.UUID;

public interface ProductService {

    ProductDtoOut create(ProductDtoIn dto);

    ProductDtoOut update(UUID id, ProductDtoFotUpdate dto);

    ProductDtoOut getById(UUID id);

    Collection<ProductDtoOut> getAll();

    void deleteAll();

    void deleteById(UUID id);

}
