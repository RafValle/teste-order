package com.teste.order.domain.repository;

import com.teste.order.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByCodigoPedido(String codigoPedido);

}
