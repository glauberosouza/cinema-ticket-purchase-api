package com.glauber.cinema.Ticket.exception;

import java.time.LocalDateTime;

public class ExceptionResponseBuilder {
    private String status;
    private Integer statusCode;
    private String error;

    public ExceptionResponseBuilder(String status, int statusCode, String error) {
        this.status = status;
        this.statusCode = statusCode;
        this.error = error;
    }
    public ExceptionResponse build(){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(this.status);
        exceptionResponse.setStatusCode(this.statusCode);
        exceptionResponse.setError(this.error);
        return exceptionResponse;
    }
}
