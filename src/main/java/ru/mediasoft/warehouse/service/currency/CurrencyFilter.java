package ru.mediasoft.warehouse.service.currency;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mediasoft.warehouse.model.CurrencyType;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CurrencyFilter extends OncePerRequestFilter {

    private final CurrencyProvider currencyProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String currencyHeader = request.getHeader("currency");
        if (currencyHeader != null) {
            currencyProvider.setCurrencyType(CurrencyType.valueOf(currencyHeader));
        }
        filterChain.doFilter(request, response);
    }
}
