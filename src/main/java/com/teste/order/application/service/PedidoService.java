package com.teste.order.application.service;

import com.teste.order.domain.model.Pedido;
import com.teste.order.domain.repository.PedidoRepository;
import com.teste.order.dto.request.PedidoRequest;
import com.teste.order.dto.response.PedidoResponse;
import com.teste.order.infrastructure.mapper.PedidoMapper;
import com.teste.order.infrastructure.messaging.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final MessageProducer messageProducer;

    @Transactional
    public PedidoResponse processarPedido(PedidoRequest request) {
        log.info("Iniciando o processamento do pedido: {}", request.getCodigoPedido());

        if (pedidoRepository.findByCodigoPedido(request.getCodigoPedido()).isPresent()) {
            log.warn("Pedido já processado anteriormente: {}", request.getCodigoPedido());
            throw new IllegalArgumentException("Pedido já processado: " + request.getCodigoPedido());
        }

        Pedido pedido = pedidoMapper.toPedido(request);
        log.debug("Pedido mapeado: {}", pedido);

        pedidoRepository.save(pedido);
        log.info("Pedido salvo no banco com ID: {}", pedido.getId());

        PedidoResponse response = pedidoMapper.toPedidoResponse(pedido);
        log.debug("Pedido convertido para response: {}", response);

        messageProducer.sendMessage(response);
        log.info("Mensagem enviada para o Kafka para o pedido: {}", response.getCodigoPedido());

        return response;
    }

    @Transactional
    public void atualizarStatusPedido(String codigoPedido, String novoStatus) {
        log.info("Atualizando status do pedido: {}, para o novo status: {}", codigoPedido, novoStatus);

        Pedido pedido = pedidoRepository.findByCodigoPedido(codigoPedido)
                .orElseThrow(() -> {
                    log.error("Pedido não encontrado: {}", codigoPedido);
                    return new IllegalArgumentException("Pedido não encontrado: " + codigoPedido);
                });

        pedido.setStatus(novoStatus);
        pedidoRepository.save(pedido);
        log.info("Status do pedido atualizado para: {} para o pedido: {}", novoStatus, codigoPedido);
    }

    public List<PedidoResponse> listarTodos() {
        log.info("Listando todos os pedidos");

        List<PedidoResponse> pedidos = pedidoRepository.findAll().stream()
                .map(pedidoMapper::toPedidoResponse)
                .collect(Collectors.toList());

        log.info("Total de pedidos encontrados: {}", pedidos.size());
        return pedidos;
    }
}
