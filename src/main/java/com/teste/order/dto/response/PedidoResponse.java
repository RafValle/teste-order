package com.teste.order.dto.response;

import com.teste.order.dto.request.ProdutoRequest;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PedidoResponse {
    private String codigoPedido;
    private List<ProdutoResponse> produtos;
    private BigDecimal valorTotal;
    private String status;
    private LocalDateTime dataRecebimento;
}