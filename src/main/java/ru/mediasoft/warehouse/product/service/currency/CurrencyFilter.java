package ru.mediasoft.warehouse.product.service.currency;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mediasoft.warehouse.product.model.CurrencyType;


import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CurrencyFilter extends OncePerRequestFilter {

    private final CurrencyProvider currencyProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String currencyHeader = request.getHeader("currency");
        Optional.ofNullable(currencyHeader)
                .map(CurrencyType::valueOf)
                .ifPresent(currencyProvider::setCurrency);
        filterChain.doFilter(request, response);
    }
}
