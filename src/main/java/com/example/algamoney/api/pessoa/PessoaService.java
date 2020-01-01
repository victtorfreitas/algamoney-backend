package com.example.algamoney.api.pessoa;

import com.example.algamoney.api.exceptionhandler.PessoaInativaOuInexistenteException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Pessoa getPessoaBD(Long codigo) {
        return pessoaRepository.findById(codigo).orElseThrow(PessoaInativaOuInexistenteException::new);
    }

    public Pessoa getPessoaAtiva(Long codigo) {
        Pessoa pessoa = pessoaRepository.findById(codigo).orElseThrow(PessoaInativaOuInexistenteException::new);
        if (pessoa.isInativo()) {
            throw new PessoaInativaOuInexistenteException();
        }
        return pessoa;
    }

    public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable) {
        return pessoaRepository.filtrarAll(pessoaFilter, pageable);
    }
}
