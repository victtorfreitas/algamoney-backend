package com.example.algamoney.api.lancamento;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
    private final LancamentoRepository lancamentoRepository;
    private LancamentoService lancamentoService;

    public LancamentoResource(LancamentoRepository lancamentoRepository,
                              LancamentoService lancamentoService) {
        this.lancamentoRepository = lancamentoRepository;
        this.lancamentoService = lancamentoService;
    }

    @GetMapping
    public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter) {
        return lancamentoRepository.pesquisar(lancamentoFilter);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Lancamento> inserir(@Valid @RequestBody Lancamento lancamento) {
        Lancamento lacamentoSalvo = lancamentoService.salvar(lancamento);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(lacamentoSalvo.getCodigo()).toUri();
        return ResponseEntity.created(uri).body(lacamentoSalvo);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> buscaPorCodigo(@PathVariable Long codigo) {
        Optional<Lancamento> lacamentoBD = lancamentoRepository.findById(codigo);
        return lacamentoBD.isPresent() ? ResponseEntity.ok(lacamentoBD.get()) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePorCodigo(@PathVariable Long codigo){
        lancamentoRepository.deleteById(codigo);
    }
}
