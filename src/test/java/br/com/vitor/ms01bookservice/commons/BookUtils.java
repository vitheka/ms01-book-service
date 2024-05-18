package br.com.vitor.ms01bookservice.commons;

import br.com.vitor.ms01bookservice.domain.Book;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookUtils {

    public Book newBook() {

        return Book.builder()
                .id(1L)
                .author("Oda")
                .title("One Piece")
                .launchDate(LocalDateTime.now())
                .price(10.80)
                .build();

    }
}
