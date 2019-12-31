package com.example.algamoney.api.usuario;

import lombok.Data;

@Data
public class UsuarioFilter {

    private String nome;
    private String email;
    private String senha;

    public UsuarioFilter(String email) {
        this.email = email;
    }
}
