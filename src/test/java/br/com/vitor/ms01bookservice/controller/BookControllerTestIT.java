package br.com.vitor.ms01bookservice.controller;

import br.com.vitor.ms01bookservice.commons.FileUtils;
import br.com.vitor.ms01bookservice.config.IntegrationTestContainers;
import br.com.vitor.ms01bookservice.proxy.CambioProxy;
import br.com.vitor.ms01bookservice.response.BookResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerTestIT extends IntegrationTestContainers {

    @Autowired
    private BookController bookController;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private CambioProxy proxy;

    @LocalServerPort
    private int port;

    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8000));
        wireMockServer.start();
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }


    @Test
    @DisplayName("findBook() return a Book when successful")
    @Sql(value = "/sql/init_one_book.sql")
    @Order(1)
    void findBook_ReturnBook_WhenSuccessful() throws Exception {

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        var expectedResponse = fileUtils.readResourceFile("get-one-book-200.json");
        var id = 1L;

        var response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .get("/v1/book-service/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().response().body().asString();

        JsonAssertions.assertThatJson(response)
                .whenIgnoringPaths("launchDate")
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("convertPrice() Return Price Book Converted")
    @Sql(value = "/sql/init_one_book_feign.sql")
    @Order(2)
    void convertPrice_ReturnPriceBookConverted_WhenSuccessful() throws Exception {

        var bookId = 2L;
        var currency = "BRL";
        var originalPrice = 10.8;

        var expectedResponse = fileUtils.readResourceFile("get-one-book-price-converted-200.json");

        wireMockServer.stubFor(WireMock.get(urlEqualTo("/v1/cambio-service/" + originalPrice + "/USD/" + currency))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-type", "application/json")
                        .withBody(expectedResponse)));

        ResponseEntity<BookResponse> response = bookController.convertPrice(bookId, currency);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JsonAssertions.assertThatJson(response)
                .whenIgnoringPaths("headers",
                        "body.launchDate", "body.price", "statusCodeValue", "statusCode")
                .isEqualTo(expectedResponse);
    }

}