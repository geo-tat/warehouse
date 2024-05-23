package ru.mediasoft.warehouse.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Данные для регистрации покупателя.")
@Getter
@Setter
@Builder
public class CustomerDtoIn {

    @NotNull
    @Schema(description = "Логин")
    private String login;

    @NotNull
    @Schema(description = "Адрес электронной почты")
    private String email;
}
