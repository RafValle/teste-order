package com.teste.order.application.service;

import com.teste.order.domain.model.Pedido;
import com.teste.order.domain.repository.PedidoRepository;
import com.teste.order.dto.request.PedidoRequest;
import com.teste.order.dto.response.PedidoResponse;
import com.teste.order.infrastructure.mapper.PedidoMapper;
import com.teste.order.infrastructure.messaging.MessageProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @Mock
    private MessageProducer messageProducer;

    @InjectMocks
    private PedidoService pedidoService;

    private PedidoRequest pedidoRequest;
    private Pedido pedido;
    private PedidoResponse pedidoResponse;


    @BeforeEach
    void setUp() {
        pedidoRequest = PedidoRequest.builder()
                .codigoPedido("12345")
                .build();

        pedido = Pedido.builder()
                .codigoPedido("12345")
                .valorTotal(BigDecimal.valueOf(500))
                .status("PROCESSADO")
                .dataRecebimento(LocalDateTime.now())
                .build();

        pedidoResponse = PedidoResponse.builder()
                .codigoPedido("12345")
                .valorTotal(BigDecimal.valueOf(500))
                .status("PROCESSADO")
                .build();
    }

    @Test
    void processarPedido_sucesso() {
        Pedido pedido = new Pedido();
        pedido.setCodigoPedido("12345");
        pedido.setValorTotal(BigDecimal.valueOf(200.0));

        PedidoResponse expectedResponse = PedidoResponse.builder()
                .codigoPedido("12345")
                .valorTotal(BigDecimal.valueOf(200.0))
                .status("PROCESSADO")
                .build();

        when(pedidoRepository.findByCodigoPedido("12345")).thenReturn(Optional.empty());
        when(pedidoMapper.toPedido(pedidoRequest)).thenReturn(pedido);
        when(pedidoMapper.toPedidoResponse(pedido)).thenReturn(expectedResponse);

        PedidoResponse response = pedidoService.processarPedido(pedidoRequest);

        assertNotNull(response);
        assertEquals("12345", response.getCodigoPedido());
        verify(messageProducer, times(1)).sendMessage(response);
    }

    @Test
    void processarPedido_jaProcessado() {
        when(pedidoRepository.findByCodigoPedido("12345")).thenReturn(Optional.of(new Pedido()));

        assertThrows(IllegalArgumentException.class, () -> pedidoService.processarPedido(pedidoRequest));
    }
    @Test
    void testAtualizarStatusPedido() {
        when(pedidoRepository.findByCodigoPedido("12345")).thenReturn(Optional.of(pedido));

        pedidoService.atualizarStatusPedido("12345", "ENTREGUE");

        verify(pedidoRepository, times(1)).save(pedido);
        assertEquals("ENTREGUE", pedido.getStatus());
    }

    @Test
    void testAtualizarStatusPedidoNotFound() {
        when(pedidoRepository.findByCodigoPedido("99999")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                pedidoService.atualizarStatusPedido("99999", "ENTREGUE")
        );
    }

    @Test
    void testProcessarPedidoWithException() {
        when(pedidoRepository.findByCodigoPedido(anyString())).thenReturn(Optional.of(pedido));

        assertThrows(IllegalArgumentException.class, () ->
                pedidoService.processarPedido(pedidoRequest)
        );
    }

    @Test
    void testListarTodos() {
        Pedido pedido1 = Pedido.builder()
                .codigoPedido("12345")
                .valorTotal(BigDecimal.valueOf(200))
                .status("PROCESSADO")
                .dataRecebimento(LocalDateTime.now())
                .build();

        Pedido pedido2 = Pedido.builder()
                .codigoPedido("67890")
                .valorTotal(BigDecimal.valueOf(300))
                .status("PROCESSADO")
                .dataRecebimento(LocalDateTime.now())
                .build();

        List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);

        when(pedidoRepository.findAll()).thenReturn(pedidos);
        when(pedidoMapper.toPedidoResponse(pedido1)).thenReturn(PedidoResponse.builder()
                .codigoPedido("12345")
                .valorTotal(BigDecimal.valueOf(200))
                .status("PROCESSADO")
                .build());

        when(pedidoMapper.toPedidoResponse(pedido2)).thenReturn(PedidoResponse.builder()
                .codigoPedido("67890")
                .valorTotal(BigDecimal.valueOf(300))
                .status("PROCESSADO")
                .build());

        List<PedidoResponse> result = pedidoService.listarTodos();

        assertEquals(2, result.size());
        assertEquals("12345", result.get(0).getCodigoPedido());
        assertEquals("67890", result.get(1).getCodigoPedido());
    }
}
