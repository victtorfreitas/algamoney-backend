package com.example.algamoney.api.config.security;

import com.example.algamoney.api.usuario.Usuario;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;


public class UsuarioSistema extends User {

    @Getter
    private Usuario usuario;

    public UsuarioSistema(Usuario usuario) {
        super(usuario.getEmail(), usuario.getSenha(), usuario.getPermissoes());
        this.usuario = usuario;
    }
}
