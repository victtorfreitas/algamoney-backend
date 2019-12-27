package com.example.algamoney.api.lancamento;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Repository
public class LancamentoRepositoryImpl implements LancamentoRepositoryCustom {

    private EntityManager em;

    public LancamentoRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter) {
        QLancamento qLancamento = QLancamento.lancamento;
        BooleanBuilder conditions = createFilter(lancamentoFilter, qLancamento);
        return new JPAQueryFactory(em).selectFrom(qLancamento).where(conditions).fetch();
    }

    private BooleanBuilder createFilter(LancamentoFilter lancamentoFilter, QLancamento qLancamento) {
        BooleanBuilder conditions = new BooleanBuilder();
        if (StringUtils.isNotEmpty(lancamentoFilter.getDescricao())) {
            conditions.and(qLancamento.descricao.likeIgnoreCase("%" + lancamentoFilter.getDescricao() + "%"));
        }
        if (Objects.nonNull(lancamentoFilter.getDataVencimentoDe())) {
            conditions.and(qLancamento.dataVencimento.gt(lancamentoFilter.getDataVencimentoDe()));
        }
        if (Objects.nonNull(lancamentoFilter.getDataVencimentoAte())) {
            conditions.and(qLancamento.dataVencimento.lt(lancamentoFilter.getDataVencimentoAte()));
        }
        return conditions;
    }
}
