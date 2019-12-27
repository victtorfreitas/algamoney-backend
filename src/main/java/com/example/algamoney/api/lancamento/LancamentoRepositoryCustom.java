package com.example.algamoney.api.lancamento;

import java.util.List;

public interface LancamentoRepositoryCustom {
    List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter);
}
