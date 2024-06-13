package ru.mediasoft.warehouse.customer.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.mediasoft.warehouse.customer.dto.CustomerDtoIn;
import ru.mediasoft.warehouse.customer.dto.CustomerDtoOut;
import ru.mediasoft.warehouse.customer.service.CustomerService;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/customers")
@Tag(name = "Покупатели", description = "Взаимодействие с покупателями")
public class CustomerController {
    private final CustomerService service;

    @PostMapping
    CustomerDtoOut create(@RequestBody CustomerDtoIn dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    CustomerDtoOut getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    Collection<CustomerDtoOut> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @PatchMapping("/{id}")
    CustomerDtoOut update(@PathVariable Long id,
                          @RequestBody CustomerDtoIn dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
