package com.example.algamoney.api.pessoa;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }


    public Pessoa update(Long codigo, Pessoa pessoa) {
        Pessoa pessoaBD = getPessoaBD(codigo);
        BeanUtils.copyProperties(pessoa, pessoaBD, "codigo");
        return pessoaRepository.save(pessoaBD);
    }

    public Pessoa updatePropriedadeAtivo(Long codigo, Boolean ativo) {
        Pessoa pessoaBD = getPessoaBD(codigo);
        pessoaBD.setAtivo(ativo);
        return pessoaRepository.save(pessoaBD);
    }

    private Pessoa getPessoaBD(Long codigo) {
        return pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
}
