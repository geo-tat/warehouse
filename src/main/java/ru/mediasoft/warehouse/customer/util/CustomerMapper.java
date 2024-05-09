package ru.mediasoft.warehouse.customer.util;

import ru.mediasoft.warehouse.customer.dto.CustomerDtoIn;
import ru.mediasoft.warehouse.customer.dto.CustomerDtoOut;
import ru.mediasoft.warehouse.customer.model.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerDtoIn dto) {
        return Customer.builder()
                .email(dto.getEmail())
                .login(dto.getLogin())
                .build();
    }

    public static CustomerDtoOut toDto(Customer customer) {
        return CustomerDtoOut.builder()
                .id(customer.getId())
                .login(customer.getLogin())
                .email(customer.getEmail())
                .build();
    }
}
