package ru.mediasoft.warehouse;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(columnDefinition = "BINARY(16)", updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
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
    private double price;

    @NotNull
    private int quantity;

    @UpdateTimestamp
    private LocalDateTime updated;

    @CreationTimestamp
    private LocalDateTime created;

}