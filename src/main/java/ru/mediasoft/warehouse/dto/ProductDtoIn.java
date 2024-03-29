package ru.mediasoft.warehouse.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Информация о поступающем на склад товаре.")
public class ProductDtoIn {
    @NotBlank(message = "Должен быть указан артикул")
    @Schema(description = "Артикул")
    private String sku;

    @NotBlank(message = "Должно быть указано название")
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Описание товара")
    private String description;

    @NotBlank(message = "Должна быть указана категория товара")
    @Schema(description = "Категория товара")
    private String category;

    @NotNull(message = "Должна быть указана цена")
    @Schema(description = "Цена товара")
    @PositiveOrZero(message = "Цена не может быть отрицательной величиной")
    private double price;

    @NotNull
    @Schema(description = "Количество товара")
    @PositiveOrZero(message = "Количество не может быть отрицательной величиной")
    private int quantity;
}
