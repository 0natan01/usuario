package com.example.usuario.infrastructure.exceptions;

public class ConflictExeptions extends RuntimeException{
    public ConflictExeptions(String mensagem){
        super(mensagem);
    }

    public ConflictExeptions(String mensagem , Throwable throwable){
        super(mensagem);
    }
}
