package com.teste.order.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class Pedido {
    private Long id;
    private String codigoPedido;
    private BigDecimal valorTotal;
    private LocalDateTime dataRecebimento;
    private String status;
}
