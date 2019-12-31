package com.example.algamoney.api.exceptionhandler;

public class PessoaInativaOuInexistenteException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Pessoa não encontrada ou desativada!";
    }
}
