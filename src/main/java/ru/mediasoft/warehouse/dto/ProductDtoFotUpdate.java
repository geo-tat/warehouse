package ru.mediasoft.warehouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Schema(description = "Информация о внесении изменений в товар товаре.")
public class ProductDtoFotUpdate {
    @Schema(description = "Артикул")
    private String sku;

    @Schema(description = "Название")
    private String name;
    @Schema(description = "Описание товара")
    private String description;

    @Schema(description = "Категория товара")
    private String category;

    @Schema(description = "Цена товара")
    @PositiveOrZero
    private BigDecimal price;

    @PositiveOrZero
    @Schema(description = "Количество товара")
    private Integer quantity;
}
