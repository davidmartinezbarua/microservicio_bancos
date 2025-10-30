package com.bancario.microservicio.exception;


public class BancoNoEncontradoException extends RuntimeException {

    public BancoNoEncontradoException(String message) {
        super(message);
    }
}