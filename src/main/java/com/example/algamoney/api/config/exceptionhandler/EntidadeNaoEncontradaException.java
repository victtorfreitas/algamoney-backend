package com.example.algamoney.api.config.exceptionhandler;

import javax.persistence.EntityNotFoundException;

public class EntidadeNaoEncontradaException extends EntityNotFoundException {

    public EntidadeNaoEncontradaException() {
        super("Entidade não encontrada!");
    }
}
