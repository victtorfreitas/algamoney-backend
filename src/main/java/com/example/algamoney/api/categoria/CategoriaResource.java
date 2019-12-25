package com.example.algamoney.api.categoria;

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
@RequestMapping("/categorias")
public class CategoriaResource {
    private final CategoriaRepository categoriaRepository;
    private ApplicationEventPublisher applicationEventPublisher;

    public CategoriaResource(CategoriaRepository categoriaRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.categoriaRepository = categoriaRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping
    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity inserir(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
        Categoria categoriaSave = categoriaRepository.save(categoria);
        applicationEventPublisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSave.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSave);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity buscaPorCodigo(@PathVariable Long codigo) {
        Optional<Categoria> categoria = categoriaRepository.findById(codigo);
        return categoria.isPresent() ? ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
    }
}
