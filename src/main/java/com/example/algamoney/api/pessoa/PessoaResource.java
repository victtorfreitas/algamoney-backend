package com.example.algamoney.api.pessoa;

import com.example.algamoney.api.config.event.RecursoCriadoEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
    private final PessoaRepository pessoaRepository;
    private ApplicationEventPublisher applicationEventPublisher;
    private PessoaService pessoaService;

    public PessoaResource(PessoaRepository pessoaRepository,
                          ApplicationEventPublisher applicationEventPublisher,
                          PessoaService pessoaService) {
        this.pessoaRepository = pessoaRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public Page<Pessoa> filtro(PessoaFilter pessoaFilter, Pageable pageable) {
        return pessoaService.filtrar(pessoaFilter, pageable);
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

    @PutMapping("/{codigo}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo,
                                            @Valid @RequestBody Pessoa pessoa) {
        Pessoa pessoaAtualizada = pessoaService.update(codigo, pessoa);
        return ResponseEntity.ok(pessoaAtualizada);
    }

    @PutMapping("/{codigo}/ativo")
    public ResponseEntity<Pessoa> atualizarPropiedadeAtivo(@PathVariable Long codigo,
                                                           @RequestBody Boolean ativo) {
        Pessoa pessoaAtualizada = pessoaService.updatePropriedadeAtivo(codigo, ativo);
        return ResponseEntity.ok(pessoaAtualizada);
    }
}
