package com.teste.order.domain.repository;

import com.teste.order.domain.model.Pedido;

import java.util.Optional;

public interface PedidoRepositoryPort {
    Optional<Pedido> findByCodigoPedido(String codigoPedido);
    Pedido save(Pedido pedido);
}
