package com.teste.order.infrastructure.messaging;

import com.teste.order.dto.response.PedidoResponse;

public interface MessageConsumer {
    void receiveMessage(PedidoResponse pedidoResponse);
}
