package ru.mediasoft.warehouse.product.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class ExchangeRate {
    private BigDecimal CNY;
    private BigDecimal EUR;
    private BigDecimal USD;
}