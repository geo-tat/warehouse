package ru.mediasoft.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mediasoft.warehouse.model.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findById(UUID id);

    void deleteById(UUID id);
}
