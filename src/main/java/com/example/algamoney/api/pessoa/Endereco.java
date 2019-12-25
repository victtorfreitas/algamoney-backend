package com.example.algamoney.api.pessoa;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Endereco {
    @Length(max = 100, message = "Campo Logradouro Não deve ter mais que 100 caracteres!")
    private String logradouro;

    @Length(max = 10, message = "Campo Numero Não deve ter mais que 10 caracteres!")
    private String numero;

    @Length(max = 150, message = "Campo bairro Não deve ter mais que 150 caracteres!")
    private String bairro;

    @Length(max = 10, message = "Campo cep Não deve ter mais que 10 caracteres!")
    private String cep;

    @Length(max = 150, message = "Campo cidade Não deve ter mais que 150 caracteres!")
    private String cidade;

    @Length(max = 100, message = "Campo estado Não deve ter mais que 100 caracteres!")
    private String estado;

}
