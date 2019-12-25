package com.example.algamoney.api.pessoa;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
    private final PessoaRepository pessoaRepository;
    private ApplicationEventPublisher applicationEventPublisher;

    public PessoaResource(PessoaRepository pessoaRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.pessoaRepository = pessoaRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping
    public List<Pessoa> listarTodos() {
        return pessoaRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Pessoa> inserir(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa pessoaSaved = pessoaRepository.save(pessoa);
        applicationEventPublisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSaved.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSaved);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> buscaPorCodigo(@PathVariable Long codigo) {
        Optional<Pessoa> pessoaBD = pessoaRepository.findById(codigo);
        return pessoaBD.isPresent() ? ResponseEntity.ok(pessoaBD.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo) {
        pessoaRepository.deleteById(codigo);
    }
}
