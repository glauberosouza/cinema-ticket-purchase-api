package com.glauber.cinema.Ticket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ExceptionResponseBuilder exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Bad request",
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }

    @ExceptionHandler(ChairNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse chairNotFoundException(ChairNotFoundException e){
        ExceptionResponseBuilder exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Bad request",
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }
    @ExceptionHandler(OccupiedChairException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse occupiedChairException(OccupiedChairException e){
        ExceptionResponseBuilder exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Bad request",
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse nullPointerException(NullPointerException e) {
        ExceptionResponseBuilder exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "A requisição falhou porque contem um elemento nulo!"
        );
        return exceptionResponseBuilder.build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse noSUchElementException(NoSuchElementException e) {
        ExceptionResponseBuilder exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Not found",
                HttpStatus.NOT_FOUND.value(),
                "Não foi localizado nenhum elemento com o valor mencionado!"
        );
        return exceptionResponseBuilder.build();
    }
}
