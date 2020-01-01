package com.example.algamoney.api.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaRepositoryCustom {
    Page<Pessoa> filtrarAll(PessoaFilter pessoaFilter, Pageable pageable);
}
