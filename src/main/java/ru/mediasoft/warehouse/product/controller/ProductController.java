package ru.mediasoft.warehouse.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.mediasoft.warehouse.product.dto.ProductDtoForUpdate;
import ru.mediasoft.warehouse.product.dto.ProductDtoIn;
import ru.mediasoft.warehouse.product.dto.ProductDtoOut;
import ru.mediasoft.warehouse.product.search.criteria.SearchCriteria;
import ru.mediasoft.warehouse.product.service.ImageService;
import ru.mediasoft.warehouse.product.service.ProductService;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
@Tag(name = "Товары", description = "Взаимодействие с товарами")
@Validated
public class ProductController {
    private final ProductService service;

    private final ImageService imageService;


    @PostMapping
    @Operation(summary = "Регистрация товара")
    ProductDtoOut create(@Valid @RequestBody ProductDtoIn dto) {
        return service.create(dto);
    }

    @Operation(summary = "Обновление информации о товаре")
    @PatchMapping("/{id}")
    ProductDtoOut update(@PathVariable UUID id, @RequestBody @Valid ProductDtoForUpdate dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Получить информацию о товаре по ID")
    @GetMapping("/{id}")
    ProductDtoOut getById(@PathVariable UUID id) {

        return service.getById(id);
    }

    @Operation(summary = "Выгрузить все товары со склада")
    @GetMapping
    Collection<ProductDtoOut> getAll(@PageableDefault(sort = "name") Pageable pageable) {
        return service.getAll(pageable);
    }

    @Operation(summary = "Удалить информацию о товаре")
    @DeleteMapping("/{id}")
    void deleteCar(@PathVariable UUID id) {
        service.deleteById(id);
    }

    @Operation(summary = "Поиск по критериям")
    @PostMapping("/search")
    public Collection<ProductDtoOut> multiCriteriaSearch(@RequestBody List<SearchCriteria<?>> criteriaList,
                                                         @PageableDefault(sort = "name") Pageable pageable) {

        return service.multiCriteriaSearch(criteriaList, pageable);
    }

    @Operation(summary = "Загрузка изображения товара")
    @PostMapping("/{id}/upload")
    String uploadImage(@PathVariable UUID id,
                       @RequestParam("file") MultipartFile file) throws IOException {

        return imageService.upload(id, file);
    }

    @Operation(summary = "Скачивание изображений товара")
    @GetMapping("/{id}/download")
    byte[] downloadImage(@PathVariable UUID id) throws IOException {

        return imageService.download(id);
    }


}
