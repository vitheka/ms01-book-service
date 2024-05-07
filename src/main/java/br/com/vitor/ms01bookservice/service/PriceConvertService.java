package br.com.vitor.ms01bookservice.service;

import br.com.vitor.ms01bookservice.domain.Book;
import br.com.vitor.ms01bookservice.proxy.CambioProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceConvertService {

    private final CambioProxy proxy;
    private final BookService bookService;
    public static final String USD = "USD";

    public Book convertPrice(Long id, String currency) {

        var book = bookService.findBook(id);

        var cambio = proxy.getCambio(book.getPrice(), USD, currency);
        book.setPrice(cambio.getConvertedValue());

        return book;
    }


}
