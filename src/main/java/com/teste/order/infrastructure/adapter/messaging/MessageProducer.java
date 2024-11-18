package com.teste.order.infrastructure.adapter.messaging;

import com.teste.order.dto.response.PedidoResponse;

public interface MessageProducer {
    void sendMessage(PedidoResponse pedidoResponse);
}
