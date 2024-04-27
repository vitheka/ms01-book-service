package br.com.vitor.ms01bookservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFoundException extends ResponseStatusException {
    public BookNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
