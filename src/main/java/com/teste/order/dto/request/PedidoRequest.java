package com.teste.order.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRequest {
    private String codigoPedido;
    private List<ProdutoRequest> produtos;
}

