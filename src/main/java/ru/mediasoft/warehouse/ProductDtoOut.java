package ru.mediasoft.warehouse;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Builder
public class ProductDtoOut {

    private UUID id;
    private String sku;
    private String name;
    private String description;
    private String category;
    private double price;
    private int quantity;
    private LocalDateTime updated;
    private LocalDateTime created;
}
