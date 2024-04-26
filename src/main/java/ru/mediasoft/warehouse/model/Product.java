package ru.mediasoft.warehouse.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Column(unique = true)
    private String sku;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String category;

    @NotNull
    private BigDecimal price;

    @NotNull
    private int quantity;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_quantity", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedQuantity;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDate created;
}