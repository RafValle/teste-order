package com.teste.order.application.service;

import com.teste.order.application.usecase.AtualizarStatusPedidoUseCase;
import com.teste.order.application.usecase.ListarTodosPedidosUseCase;
import com.teste.order.application.usecase.ProcessarPedidoUseCase;
import com.teste.order.dto.request.PedidoRequest;
import com.teste.order.dto.response.PedidoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final ProcessarPedidoUseCase processarPedidoUseCase;
    private final AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;
    private final ListarTodosPedidosUseCase listarTodosPedidosUseCase;


    public PedidoResponse processarPedido(PedidoRequest request) {
        return processarPedidoUseCase.executar(request);
    }

    public void atualizarStatusPedido(String codigoPedido, String novoStatus) {
        atualizarStatusPedidoUseCase.executar(codigoPedido, novoStatus);
    }

    public List<PedidoResponse> listarTodos() {
        return listarTodosPedidosUseCase.executar();
    }
}
