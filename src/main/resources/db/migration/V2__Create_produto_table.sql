CREATE TABLE IF NOT EXISTS produto (
    id SERIAL PRIMARY KEY,
    codigo_produto VARCHAR(50) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    quantidade INT NOT NULL,
    valor_unitario DECIMAL(15, 2) NOT NULL,
    valor_total DECIMAL(15, 2) NOT NULL,
    pedido_id INT REFERENCES pedido(id) ON DELETE CASCADE
);
