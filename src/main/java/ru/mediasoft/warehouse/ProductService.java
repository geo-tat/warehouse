package ru.mediasoft.warehouse;

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
