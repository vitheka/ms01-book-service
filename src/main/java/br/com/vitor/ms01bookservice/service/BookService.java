package br.com.vitor.ms01bookservice.service;

import br.com.vitor.ms01bookservice.domain.Book;
import br.com.vitor.ms01bookservice.exception.BookNotFoundException;
import br.com.vitor.ms01bookservice.proxy.CambioProxy;
import br.com.vitor.ms01bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final Environment environment;
    @Autowired
    private CambioProxy proxy;


    public Book findBook(Long id, String currency) {

        var book = repository.findById(id).orElseThrow(() -> new BookNotFoundException("Book Not found"));

        var port = environment.getProperty("local.server.port");
        book.setEnvironment(port);

        var cambio = proxy.getCambio(book.getPrice(), "USD", currency);
        book.setPrice(cambio.getConvertedValue());

        return book;
    }
}
