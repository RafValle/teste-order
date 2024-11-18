package com.teste.order.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PedidoRequest {
    private String codigoPedido;
    private List<ProdutoRequest> produtos;
}

