package com.example.algamoney.api.pessoa;

import com.example.algamoney.api.Utils.ConditionUtils;
import com.example.algamoney.api.Utils.PageUtils;
import com.example.algamoney.api.base.RepositoryDefault;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PessoaRepositoryImpl extends RepositoryDefault implements PessoaRepositoryCustom {

    public PessoaRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Page<Pessoa> filtrarAll(PessoaFilter pessoaFilter, Pageable pageable) {
        QPessoa qPessoa = QPessoa.pessoa;

        BooleanBuilder conditions = createFilter(pessoaFilter, qPessoa);

        JPAQuery<Pessoa> query = queryFactory().selectFrom(qPessoa).where(conditions);

        return (Page<Pessoa>) PageUtils.paginate(pageable, query);
    }

    private BooleanBuilder createFilter(PessoaFilter pessoaFilter, QPessoa qPessoa) {
        BooleanBuilder conditions = new BooleanBuilder();

        conditions.and(ConditionUtils.filter(pessoaFilter.getAtivo(), qPessoa.ativo));
        conditions.and(ConditionUtils.filter(pessoaFilter.getNome(), qPessoa.nome));
        conditions.and(ConditionUtils.filter(pessoaFilter.getCep(), qPessoa.endereco.cep));
        conditions.and(ConditionUtils.filter(pessoaFilter.getLogradouro(), qPessoa.endereco.logradouro));

        return conditions;
    }

}
