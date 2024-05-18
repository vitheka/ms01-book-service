package br.com.vitor.ms01bookservice.service;


import br.com.vitor.ms01bookservice.commons.BookUtils;
import br.com.vitor.ms01bookservice.domain.Book;
import br.com.vitor.ms01bookservice.exception.BookNotFoundException;
import br.com.vitor.ms01bookservice.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookServiceTest {


    @InjectMocks
    private BookService bookService;

    @InjectMocks
    private BookUtils bookUtils;

    @Mock
    private BookRepository repository;

    private Book bookInit;

    @BeforeEach
    void init() {

        bookInit = bookUtils.newBook();
    }

    @Test
    @DisplayName("findBook() Returns a Book")
    @Order(1)
    void findBook_ReturnBook_WhenSuccessful() {

        var bookId = 1L;
        var book = bookInit;

        when(repository.findById(bookId)).thenReturn(Optional.of(book));

        var bookFound = bookService.findBook(bookId);

        assertThat(bookFound).isNotNull().isEqualTo(book);

    }

    @Test
    @DisplayName("findBook() ThrowNotFoundException When book Not Found")
    @Order(2)
    void findBook_ThrowNotFoundException_WhenError() {

        var invalidBookId = 999L;

        when(repository.findById(invalidBookId)).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> bookService.findBook(invalidBookId))
                .isInstanceOf(BookNotFoundException.class);

    }

}

