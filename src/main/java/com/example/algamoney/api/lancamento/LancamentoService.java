package com.example.algamoney.api.lancamento;

import com.example.algamoney.api.pessoa.PessoaService;
import org.springframework.stereotype.Service;

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
}
