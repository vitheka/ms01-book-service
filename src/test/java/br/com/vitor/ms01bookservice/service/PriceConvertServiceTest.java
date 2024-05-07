package br.com.vitor.ms01bookservice.service;

import br.com.vitor.ms01bookservice.domain.Book;
import br.com.vitor.ms01bookservice.proxy.CambioProxy;
import br.com.vitor.ms01bookservice.repository.BookRepository;
import br.com.vitor.ms01bookservice.response.CambioResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PriceConvertServiceTest {

    @InjectMocks
    private PriceConvertService priceConvertService;

    @Mock
    private CambioProxy cambioProxy;

    @Mock
    private BookService bookService;

    private CambioResponse cambioInit;

    private static final String TARGETCURRENCY = "EUR";

    private Book bookInit;

    @BeforeEach
    void init() {

        cambioInit = CambioResponse.builder()
                .id(10L)
                .from("USD")
                .to("BRL")
                .conversionFactor(BigDecimal.valueOf(5))
                .convertedValue(50.8)
                .build();

        bookInit = Book.builder()
                .id(1L)
                .author("Oda")
                .title("One Piece")
                .launchDate(LocalDateTime.now())
                .price(10.80)
                .build();
    }

    @Test
    @DisplayName("convertPrice() Return Price Book Converted")
    @Order(1)
    void convertPrice_ReturnPriceBookConverted_WhenSuccessful() {

        var bookId = 1L;
        var book = bookInit;
        var cambio = cambioInit;

        when(bookService.findBook(bookId)).thenReturn((book));
        when(cambioProxy.getCambio(bookInit.getPrice(), "USD",TARGETCURRENCY)).thenReturn(cambio);

        var result = priceConvertService.convertPrice(bookId, TARGETCURRENCY);

        assertThat(result).isNotNull();
        assertThat(result.getPrice()).isEqualTo(cambio.getConvertedValue());

    }


}