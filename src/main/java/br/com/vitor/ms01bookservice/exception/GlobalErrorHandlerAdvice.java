package br.com.vitor.ms01bookservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalErrorHandlerAdvice {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<DefaultErrorMessage> handleBookNotFoundException(BookNotFoundException ex) {
        var errorResponse = new DefaultErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getReason());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


}
