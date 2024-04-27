package br.com.vitor.ms01bookservice.controller;

import br.com.vitor.ms01bookservice.exception.BookNotFoundException;
import br.com.vitor.ms01bookservice.proxy.CambioProxy;
import br.com.vitor.ms01bookservice.repository.BookRepository;
import br.com.vitor.ms01bookservice.domain.Book;
import io.micrometer.observation.annotation.Observed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.core.env.Environment;
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

    private final BookRepository repository;
    private final Environment environment;

    @Autowired
    private  CambioProxy proxy;


    @Observed
    @Operation(summary = "Get Cambio from currency")
    @GetMapping("/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {

        var book = repository.findById(id).orElseThrow(() -> new BookNotFoundException("Book Not found"));

        var port = environment.getProperty("local.server.port");
        book.setEnvironment(port);

        var cambio = proxy.getCambio(book.getPrice(), "USD", currency);
        book.setPrice(cambio.getConvertedValue());

        return book;
    }
}
