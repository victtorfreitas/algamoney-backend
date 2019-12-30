package com.example.algamoney.api.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Page<Usuario> pesquisar(UsuarioFilter usuarioFilter, Pageable pageable) {
        return usuarioRepository.pesquisar(usuarioFilter, pageable);
    }

    public Optional<Usuario> findById(Long codigo) {
        return usuarioRepository.findById(codigo);
    }

    public void deleteById(Long codigo) {
        usuarioRepository.deleteById(codigo);
    }
}
