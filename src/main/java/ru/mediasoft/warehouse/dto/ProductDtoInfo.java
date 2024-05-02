package ru.mediasoft.warehouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductDtoInfo {
    @Schema(description = "id")
    private UUID id;
    @Schema(description = "Название")
    private String name;
}
