package com.example.algamoney.api.lancamento;

import com.example.algamoney.api.pessoa.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoService {

    private LancamentoRepository lancamentoRepository;
    private PessoaService pessoaService;

    public LancamentoService(LancamentoRepository lancamentoRepository,
                             PessoaService pessoaService) {
        this.lancamentoRepository = lancamentoRepository;
        this.pessoaService = pessoaService;
    }

    public Lancamento salvar(Lancamento lancamento) {
        pessoaService.getPessoaAtiva(lancamento.getPessoa().getCodigo());
        return lancamentoRepository.save(lancamento);
    }

    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepository.pesquisar(lancamentoFilter, pageable);
    }

    public Optional<Lancamento> findById(Long codigo) {
        return lancamentoRepository.findById(codigo);
    }

    public void deleteById(Long codigo) {
        lancamentoRepository.deleteById(codigo);
    }
}
