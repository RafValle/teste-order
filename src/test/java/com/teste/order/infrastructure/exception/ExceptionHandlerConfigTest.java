package com.teste.order.infrastructure.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExceptionHandlerConfigTest {

    private ExceptionHandlerConfig exceptionHandlerConfig;

    @BeforeEach
    void setUp() {
        exceptionHandlerConfig = new ExceptionHandlerConfig();
    }

    @Test
    void testHandlePedidoNotFoundException() {
        PedidoNotFoundException exception = new PedidoNotFoundException("Pedido 123 não encontrado");

        ResponseEntity<Map<String, Object>> response = exceptionHandlerConfig.handlePedidoNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Pedido não encontrado", response.getBody().get("error"));
        assertEquals("Pedido 123 não encontrado", response.getBody().get("message"));
    }

    @Test
    void testHandleGeneralException() {
        Exception exception = new RuntimeException("Erro inesperado");
        WebRequest request = new ServletWebRequest(new MockHttpServletRequest());

        ResponseEntity<Map<String, Object>> response = exceptionHandlerConfig.handleGeneralException(exception, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Erro interno do servidor", response.getBody().get("error"));
        assertEquals("Erro inesperado", response.getBody().get("message"));
    }
}