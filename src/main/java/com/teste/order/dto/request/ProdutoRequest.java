package com.teste.order.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoRequest {
    private String nome;
    private BigDecimal valor;
}
