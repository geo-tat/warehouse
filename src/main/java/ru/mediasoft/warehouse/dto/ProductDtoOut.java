package ru.mediasoft.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.mediasoft.warehouse.model.CategoryType;
import ru.mediasoft.warehouse.model.CurrencyType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductDtoOut {

    private UUID id;
    @Schema(description = "Артикул")
    private String sku;
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Описание товара")
    private String description;
    @Schema(description = "Категория товара")
    private CategoryType category;
    @Schema(description = "Цена товара")
    private BigDecimal price;
    @Schema(description = "Количество товара")
    private int quantity;
    @Schema(description = "Дата изменения количества товара")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;
    @Schema(description = "Дата создания")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;
    @Schema(description = "Валюта")
    private CurrencyType currencyType;
}
