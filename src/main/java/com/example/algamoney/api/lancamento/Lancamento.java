package com.example.algamoney.api.lancamento;

import com.example.algamoney.api.categoria.Categoria;
import com.example.algamoney.api.pessoa.Pessoa;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "lancamento")
@Data
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull(message = "Descricao é obrigatório!")
    private String descricao;

    @NotNull(message = "Data de vencimento é obrigatório!")
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @NotNull(message = "Valor é obrigatório!")
    private BigDecimal valor;

    private String observacao;

    @NotNull(message = "Tipo é obrigatório!")
    @Enumerated(EnumType.STRING)
    private TipoLancamento tipo;

    @NotNull(message = "Categoria é obrigatório!")
    @ManyToOne
    @JoinColumn(name = "codigo_categoria")
    private Categoria categoria;

    @NotNull(message = "Pessoa é obrigatório!")
    @ManyToOne
    @JoinColumn(name = "codigo_pessoa")
    private Pessoa pessoa;

}
