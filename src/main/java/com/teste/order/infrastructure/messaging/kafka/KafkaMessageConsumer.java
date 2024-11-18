package com.teste.order.infrastructure.messaging.kafka;

import com.teste.order.application.service.PedidoService;
import com.teste.order.dto.response.PedidoResponse;
import com.teste.order.infrastructure.messaging.MessageConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageConsumer implements MessageConsumer {
    private final PedidoService pedidoService;

    @Override
    @KafkaListener(topics = "pedidos-processados", groupId = "order-group")
    public void receiveMessage(PedidoResponse pedidoResponse) {
        log.info("Mensagem recebida do Kafka: {}", pedidoResponse.getCodigoPedido());

        try {
            pedidoService.atualizarStatusPedido(pedidoResponse.getCodigoPedido(), "ENTREGUE");
            log.info("Status do pedido atualizado para ENTREGUE: {}", pedidoResponse.getCodigoPedido());
        } catch (Exception e) {
            log.error("Erro ao atualizar status do pedido: {}", e.getMessage(), e);
        }
    }
}