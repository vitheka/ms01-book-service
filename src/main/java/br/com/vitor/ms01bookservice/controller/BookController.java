package br.com.vitor.ms01bookservice.controller;

import br.com.vitor.ms01bookservice.mapper.BookMapper;
import br.com.vitor.ms01bookservice.response.BookResponse;
import br.com.vitor.ms01bookservice.service.BookService;
import br.com.vitor.ms01bookservice.service.PriceConvertService;
import io.micrometer.observation.annotation.Observed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping("/v1/book-service")
@RequiredArgsConstructor
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@Log4j2
public class BookController {

    private final BookService bookService;
    private final PriceConvertService priceConvertService;

    private final BookMapper mapper;

    @Observed
    @Operation(summary = "Get Cambio from currency")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findBook(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.bookToBookresponse(bookService.findBook(id)));
    }

    @Observed
    @Operation(summary = "Get Book with converted price")
    @GetMapping("/{id}/{currency}")
    public ResponseEntity<BookResponse> convertPrice(@PathVariable("id") Long id, @PathVariable("currency") String currency) {
        return ResponseEntity.ok(mapper.bookToBookresponse(priceConvertService.convertPrice(id, currency)));
    }
}
