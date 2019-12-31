package com.example.algamoney.api.lancamento;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.Expression;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LancamentoDTO {
    private Long codigo;
    private String descricao;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private String tipo;
    private String categoria;
    private String pessoa;

    @QueryProjection
    public LancamentoDTO(Long codigo, String descricao,
                         LocalDate dataVencimento, LocalDate dataPagamento,
                         BigDecimal valor, String tipo, String categoria, String pessoa) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
        this.pessoa = pessoa;
    }

    public static Expression<?> constructFullExpression(QLancamento qLancamento) {
        return new QLancamentoDTO(qLancamento.codigo,
                qLancamento.descricao,
                qLancamento.dataVencimento,
                qLancamento.dataPagamento,
                qLancamento.valor,
                qLancamento.tipo.stringValue(),
                qLancamento.categoria.nome,
                qLancamento.pessoa.nome);
    }
}
