package ru.mediasoft.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mediasoft.warehouse.model.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findById(UUID id);

    void deleteById(UUID id);

    Page<Product> findAll(Specification<Product> specification, Pageable pageable);
}
