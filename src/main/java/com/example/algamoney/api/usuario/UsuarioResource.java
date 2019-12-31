package com.example.algamoney.api.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
    private UsuarioService usuarioService;

    public UsuarioResource(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public Page<Usuario> pesquisar(UsuarioFilter usuarioFilter, Pageable pageable) {
        return usuarioService.pesquisar(usuarioFilter, pageable);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Usuario> inserir(@Valid @RequestBody Usuario usuario) {
        Usuario lacamentoSalvo = usuarioService.salvar(usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(lacamentoSalvo.getCodigo()).toUri();
        return ResponseEntity.created(uri).body(lacamentoSalvo);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> buscaPorCodigo(@PathVariable Long codigo) {
        Optional<Usuario> lacamentoBD = usuarioService.findById(codigo);
        return lacamentoBD.isPresent() ? ResponseEntity.ok(lacamentoBD.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePorCodigo(@PathVariable Long codigo) {
        usuarioService.deleteById(codigo);
    }
}
