package com.example.algamoney.api.lancamento;

import com.example.algamoney.api.Utils.PageUtils;
import com.example.algamoney.api.base.RepositoryDefault;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Objects;

@Repository
public class LancamentoRepositoryImpl extends RepositoryDefault implements LancamentoRepositoryCustom {

    public LancamentoRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        QLancamento qLancamento = QLancamento.lancamento;
        BooleanBuilder conditions = createFilter(lancamentoFilter, qLancamento);
        JPAQuery<Lancamento> query = queryFactory().selectFrom(qLancamento).where(conditions);
        return (Page<Lancamento>) PageUtils.paginate(pageable, query);
    }

    @Override
    public Page<LancamentoDTO> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
        QLancamento qLancamento = QLancamento.lancamento;
        BooleanBuilder conditions = createFilter(lancamentoFilter, qLancamento);
        JPAQuery<?> query = queryFactory()
                .select(LancamentoDTO.constructFullExpression(qLancamento))
                .from(qLancamento)
                .where(conditions);
        return (Page<LancamentoDTO>) PageUtils.paginate(pageable, query);
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
