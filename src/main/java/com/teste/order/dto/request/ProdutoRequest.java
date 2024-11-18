package com.teste.order.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoRequest {
    private String codigoProduto;
    private String nomeProduto;
    private BigDecimal valorUnitario;
    private int quantidade;
}
