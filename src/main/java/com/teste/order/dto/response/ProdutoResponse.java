package com.teste.order.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import lombok.Builder;


@Data
@Builder
public class ProdutoResponse {
    private String codigoProduto;
    private String nome;
    private int quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;

    public static ProdutoResponse fromRequest(String codigoProduto, String nome, int quantidade, BigDecimal valorUnitario) {
        return ProdutoResponse.builder()
                .codigoProduto(codigoProduto)
                .nome(nome)
                .quantidade(quantidade)
                .valorUnitario(valorUnitario)
                .valorTotal(valorUnitario.multiply(BigDecimal.valueOf(quantidade)))
                .build();
    }
}