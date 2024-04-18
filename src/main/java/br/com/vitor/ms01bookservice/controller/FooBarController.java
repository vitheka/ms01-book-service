package br.com.vitor.ms01bookservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Tag(name = "Foo endpoint")
@RestController
@RequestMapping("/v1/book-service")
@Log4j2
public class FooBarController {

    @Operation(summary = "Foo")
    @GetMapping("/foo-bar")
    @Retry(name = "foo-bar", fallbackMethod = "fallbackMethod")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")
    public String fooBar() {

        log.info("Request to foo-bar is received!");

        var response = new RestTemplate().getForEntity("http://localhost:8080/foo-bar", String.class);
        return response.getBody();
    }

    public String fallbackMethod(Exception e) {
        return  "FallBackMethod!";
    }
}
