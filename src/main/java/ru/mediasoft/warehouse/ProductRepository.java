package ru.mediasoft.warehouse;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
   Optional<Product> findById(UUID id);

   void deleteById(UUID id);
}
