package com.example.algamoney.api.pessoa;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
    private final PessoaRepository pessoaRepository;

    public PessoaResource(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @GetMapping
    public List<Pessoa> listarTodos() {
        return pessoaRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity inserir(@Valid @RequestBody Pessoa pessoa) {
        Pessoa pessoaSaved = pessoaRepository.save(pessoa);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(pessoaSaved.getCodigo()).toUri();
        return ResponseEntity.created(uri).body(pessoaSaved);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity buscaPorCodigo(@PathVariable Long codigo) {
        Optional<Pessoa> pessoaBD = pessoaRepository.findById(codigo);
        return pessoaBD.isPresent() ? ResponseEntity.ok(pessoaBD.get()) : ResponseEntity.notFound().build();
    }
}
