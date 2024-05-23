package ru.mediasoft.warehouse.customer.service;

import org.springframework.data.domain.Pageable;
import ru.mediasoft.warehouse.customer.dto.CustomerDtoIn;
import ru.mediasoft.warehouse.customer.dto.CustomerDtoOut;

import java.util.Collection;

public interface CustomerService {

    CustomerDtoOut create(CustomerDtoIn dto);

    CustomerDtoOut update(Long id, CustomerDtoIn dto);

    CustomerDtoOut getById(Long id);

    Collection<CustomerDtoOut> getAll(Pageable pageable);

    void delete(Long id);
}
