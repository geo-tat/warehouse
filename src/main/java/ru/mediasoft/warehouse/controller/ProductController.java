package ru.mediasoft.warehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;
import ru.mediasoft.warehouse.dto.ProductDtoFotUpdate;
import ru.mediasoft.warehouse.dto.ProductDtoIn;
import ru.mediasoft.warehouse.dto.ProductDtoOut;
import ru.mediasoft.warehouse.service.ProductService;

import java.util.Collection;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
@Tag(name = "Товары", description = "Взаимодействие с товарами")
public class ProductController {
    private final ProductService service;


    @PostMapping
    @Operation(summary = "Регистрация товара")
    ProductDtoOut create(@Valid @RequestBody ProductDtoIn dto) {
        return service.create(dto);
    }

    @Operation(summary = "Обновление информации о товаре")
    @PatchMapping("/{id}")
    ProductDtoOut update(@PathVariable UUID id, @RequestBody @Valid ProductDtoFotUpdate dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Получить информацию о товаре по ID")
    @GetMapping("/{id}")
    ProductDtoOut getById(@PathVariable UUID id) {

        return service.getById(id);
    }

    @Operation(summary = "Выгрузить все товары со склада")
    @GetMapping
    Collection<ProductDtoOut> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Удалить информацию о товаре")
    @DeleteMapping("/{id}")
    void deleteCar(@PathVariable UUID id) {
        service.deleteById(id);
    }

    @Operation(summary = "Удалить всю информацию о товаре")
    @DeleteMapping
    void deleteAllCars() {
        service.deleteAll();
    }
}
