package ru.mediasoft.warehouse.customer.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mediasoft.warehouse.customer.util.CustomerMapper;
import ru.mediasoft.warehouse.customer.dto.CustomerDtoIn;
import ru.mediasoft.warehouse.customer.dto.CustomerDtoOut;
import ru.mediasoft.warehouse.customer.model.Customer;
import ru.mediasoft.warehouse.customer.repository.CustomerRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;

    @Override
    public CustomerDtoOut create(CustomerDtoIn dto) {
        Customer customerToSave = CustomerMapper.toEntity(dto);
        return CustomerMapper.toDto(repository.save(customerToSave));
    }

    @Override
    public CustomerDtoOut update(Long id, CustomerDtoIn dto) {
        return null;
    }

    @Override
    public CustomerDtoOut getById(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Покупателя с данным Id нет в базе данных"));
        return CustomerMapper.toDto(customer);
    }

    @Override
    public Collection<CustomerDtoOut> getAll(Pageable pageable) {
        Page<Customer> customers = repository.findAll(pageable);
        return customers.stream()
                .map(CustomerMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Покупателя с данным Id нет в базе данных"));

        repository.delete(customer);
    }
}
