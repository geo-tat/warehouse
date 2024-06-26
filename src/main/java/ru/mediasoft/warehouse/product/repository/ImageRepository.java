package ru.mediasoft.warehouse.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mediasoft.warehouse.product.model.Image;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByProductId(UUID id);
}
