package com.example.algamoney.api.pessoa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "pessoa")
@Data
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull(message = "Nome é obrigatório!")
    @Length(max = 250, message = "Nome não deve ter mais que 250 caractéres!")
    private String nome;

    @NotNull(message = "Campo Ativo é obrigatório!")
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    @JsonIgnore
    @Transient
    public boolean isInativo() {
        return !this.ativo;
    }
}
