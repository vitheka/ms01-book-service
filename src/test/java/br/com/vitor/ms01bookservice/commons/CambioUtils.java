package br.com.vitor.ms01bookservice.commons;

import br.com.vitor.ms01bookservice.domain.Book;
import br.com.vitor.ms01bookservice.response.CambioResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class CambioUtils {

    public CambioResponse newCambio() {

        return CambioResponse.builder()
                .id(10L)
                .from("USD")
                .to("BRL")
                .conversionFactor(BigDecimal.valueOf(5))
                .convertedValue(50.8)
                .build();
    }
}
