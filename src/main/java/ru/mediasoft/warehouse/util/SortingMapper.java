package ru.mediasoft.warehouse.util;

import org.springframework.data.domain.Sort;

public class SortingMapper {

    public static Sort sorting(String sort) {
        String[] sortParams = sort.split(",");
        String sortBy = sortParams[0];
        String sortOrder = sortParams[1];
        Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(direction, sortBy);
    }
}
