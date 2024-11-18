package com.teste.order.infrastructure.mapper;
import com.teste.order.domain.model.Pedido;

import com.teste.order.domain.model.Produto;
import com.teste.order.dto.request.PedidoRequest;
import com.teste.order.dto.request.ProdutoRequest;
import com.teste.order.dto.response.PedidoResponse;
import com.teste.order.dto.response.ProdutoResponse;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public Pedido toPedido(PedidoRequest request) {
        Pedido pedido = new Pedido();
        pedido.setCodigoPedido(request.getCodigoPedido());
        pedido.setDataRecebimento(LocalDateTime.now());
        pedido.setStatus("PROCESSADO");

        List<Produto> produtos = request.getProdutos().stream()
                .map(this::toProduto)
                .collect(Collectors.toList());

        BigDecimal valorTotal = produtos.stream()
                .map(Produto::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setValorTotal(valorTotal);
        pedido.setProdutos(produtos);

        produtos.forEach(produto -> produto.setPedido(pedido));
        return pedido;
    }

    public Produto toProduto(ProdutoRequest request) {
        Produto produto = new Produto();
        produto.setCodigoProduto(request.getCodigoProduto());
        produto.setNomeProduto(request.getNomeProduto());
        produto.setValorUnitario(request.getValorUnitario());
        produto.setQuantidade(request.getQuantidade());
        produto.setValorTotal(request.getValorUnitario().multiply(BigDecimal.valueOf(request.getQuantidade())));
        return produto;
    }

    public PedidoResponse toPedidoResponse(Pedido pedido) {
        List<ProdutoResponse> produtosResponse = pedido.getProdutos().stream()
                .map(this::toProdutoResponse)
                .collect(Collectors.toList());

        return PedidoResponse.builder()
                .codigoPedido(pedido.getCodigoPedido())
                .valorTotal(pedido.getValorTotal())
                .status(pedido.getStatus())
                .dataRecebimento(pedido.getDataRecebimento())
                .produtos(produtosResponse)
                .build();
    }

    public ProdutoResponse toProdutoResponse(Produto produto) {
        return ProdutoResponse.builder()
                .codigoProduto(produto.getCodigoProduto())
                .nomeProduto(produto.getNomeProduto())
                .valorUnitario(produto.getValorUnitario())
                .quantidade(produto.getQuantidade())
                .valorTotal(produto.getValorTotal())
                .build();
    }
}

