package com.example.algamoney.api.lancamento;

import com.example.algamoney.api.config.exceptionhandler.EntidadeNaoEncontradaException;
import com.example.algamoney.api.pessoa.Pessoa;
import com.example.algamoney.api.pessoa.PessoaService;
import org.springframework.beans.BeanUtils;
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
        pessoaService.isValid(lancamento.getPessoa().getCodigo());
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

    public Page<LancamentoDTO> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepository.resumir(lancamentoFilter, pageable);
    }

    public Lancamento atualizaLancamento(Lancamento lancamento) {
        Lancamento lancamentoBD = buscaLancamento(lancamento.getCodigo());
        validaPessoaLancamento(lancamentoBD.getPessoa(), lancamento.getPessoa());

        BeanUtils.copyProperties(lancamento, lancamentoBD, "codigo");
        return lancamentoRepository.save(lancamentoBD);
    }

    private void validaPessoaLancamento(Pessoa pessoaBD, Pessoa pessoa) {
        if (pessoaBD.getCodigo() != pessoa.getCodigo()) {
            pessoaService.isValid(pessoa.getCodigo());
        }
    }

    private Lancamento buscaLancamento(Long codigo) {
        return lancamentoRepository.findById(codigo).orElseThrow(() -> new EntidadeNaoEncontradaException());
    }
}
