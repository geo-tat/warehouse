package ru.mediasoft.warehouse;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
        if (dto.getQuantity() != null)
            productToUpdate.setQuantity(dto.getQuantity());
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
}
