package com.example.algamoney.api.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryCustom {
    Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable);
}
