package br.com.vitor.ms01bookservice.controller;

import br.com.vitor.ms01bookservice.repository.BookRepository;
import br.com.vitor.ms01bookservice.domain.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/book-service")
@RequiredArgsConstructor
@Log4j2
public class BookController {

    private final BookRepository repository;
    private final Environment environment;

    @GetMapping("/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {

        var book = repository.findById(id).orElseThrow(() -> new RuntimeException("Book Not found"));

        var port = environment.getProperty("local.server.port");
        book.setEnvironment(port);

        return book;
    }
}
