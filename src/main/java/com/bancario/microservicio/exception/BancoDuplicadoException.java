package com.bancario.microservicio.exception;

public class BancoDuplicadoException extends RuntimeException {

    public BancoDuplicadoException(String message) {
        super(message);
    }
}