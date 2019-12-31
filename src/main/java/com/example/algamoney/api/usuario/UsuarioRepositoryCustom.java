package com.example.algamoney.api.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UsuarioRepositoryCustom {
    Page<Usuario> pesquisar(UsuarioFilter usuarioFilter, Pageable pageable);

    Optional<Usuario> findByEmail(String email);
}
