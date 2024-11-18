package com.teste.order.application.usecase;

import com.teste.order.domain.model.Pedido;
import com.teste.order.domain.repository.PedidoRepository;
import com.teste.order.dto.response.PedidoResponse;
import com.teste.order.infrastructure.mapper.PedidoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarPedidosUseCaseTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @InjectMocks
    private ListarTodosPedidosUseCase listarPedidosUseCase;

    private Pedido pedido;

    private PedidoResponse pedidoResponse;

    @BeforeEach
    void setUp() {
        pedido = Pedido.builder()
                .codigoPedido("12345")
                .status("PROCESSADO")
                .valorTotal(BigDecimal.valueOf(200.0))
                .dataRecebimento(LocalDateTime.now())
                .build();

        pedidoResponse = PedidoResponse.builder()
                .codigoPedido("12345")
                .status("PROCESSADO")
                .valorTotal(BigDecimal.valueOf(200.0))
                .build();
    }

    @Test
    void deveRetornarTodosOsPedidos() {
        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));
        when(pedidoMapper.toPedidoResponse(pedido)).thenReturn(pedidoResponse);

        List<PedidoResponse> pedidos = listarPedidosUseCase.executar();

        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
        assertEquals("12345", pedidos.get(0).getCodigoPedido());

        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverPedidos() {
        when(pedidoRepository.findAll()).thenReturn(Collections.emptyList());

        List<PedidoResponse> pedidos = listarPedidosUseCase.executar();

        assertNotNull(pedidos);
        assertTrue(pedidos.isEmpty());

        verify(pedidoRepository, times(1)).findAll();
    }
}
