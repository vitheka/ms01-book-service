package br.com.vitor.ms01bookservice.service;

import br.com.vitor.ms01bookservice.domain.Book;
import br.com.vitor.ms01bookservice.exception.BookNotFoundException;
import br.com.vitor.ms01bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public Book findBook(Long id) {

        return repository.findById(id).orElseThrow(
                () -> new BookNotFoundException("Book Not found"));
    }

}
