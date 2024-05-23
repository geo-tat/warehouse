package ru.mediasoft.warehouse.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.mediasoft.warehouse.product.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    Optional<Product> findById(UUID id);

    void deleteById(UUID id);

    List<Product> findAllByIdInAndIsAvailableTrue(List<UUID> ids);
}
