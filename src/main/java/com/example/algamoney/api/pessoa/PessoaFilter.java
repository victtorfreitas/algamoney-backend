package com.example.algamoney.api.pessoa;

import lombok.Data;

@Data
public class PessoaFilter {
    private String nome;
    private Boolean ativo;
    private String logradouro;
    private String cep;
}
