package ru.mediasoft.warehouse;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Информация о поступающем на склад товаре.")
public class ProductDtoIn {
    @NotBlank
    @Schema(description = "Артикул")
    private String sku;

    @NotBlank
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Описание товара")
    private String description;

    @NotBlank
    @Schema(description = "Категория товара")
    private String category;

    @NotNull
    @Schema(description = "Цена товара")
    private double price;

    @NotNull
    @Schema(description = "Количество товара")
    private int quantity;
}
