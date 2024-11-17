package com.teste.order.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Produto {
    private String nome;
    private BigDecimal valor;
}