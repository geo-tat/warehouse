package ru.mediasoft.warehouse.customer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Информация о покупателе.")
public class CustomerDtoOut {
    @Schema(description = "id")
    private Long id;
    @Schema(description = "логин")
    private String login;
    @Schema(description = "адрес электронной почты")
    private String email;
}
