package ru.mediasoft.warehouse.customer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerInfo {
    private Long id;

    private String accountNumber;

    private String email;

    private String inn;
}
