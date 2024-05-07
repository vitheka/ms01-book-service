package br.com.vitor.ms01bookservice.response;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CambioResponse {

    private Long id;
    private String from;
    private String to;
    private BigDecimal conversionFactor;
    private Double convertedValue;
    private String environment;

}
