package ru.mediasoft.warehouse.product.model;

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
@Table(name = "product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "sku", unique = true)
    private String sku;

    @NotBlank
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @NotNull
    @Column(name = "quantity")
    private int quantity;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_quantity", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedQuantity;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDate created;

    @NotNull
    @Column(name = "isAvailable")
    private boolean isAvailable;
}