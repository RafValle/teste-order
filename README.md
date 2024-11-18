
# 📦 Order

**Order** é um sistema desenvolvido em **Java com Spring Boot** que gerencia pedidos recebidos de um sistema externo (Sistema A), processa esses pedidos, calcula o valor total e envia o resultado para outro sistema externo (Sistema B) via **Apache Kafka**.

---

## 🚀 Como Rodar o Projeto

### 1. Clonar o Repositório
```bash
git clone https://github.com/RafValle/teste-order.git
cd teste-order
```

### 2. Subir os Containers com Docker Compose
Certifique-se de que o Docker está instalado e rodando:
```bash
docker-compose up -d
```

### 3. Compilar e Executar o Projeto
Certifique-se de que você possui o Maven instalado e configurado corretamente:
```bash
mvn clean install
mvn spring-boot:run
```

### 4. Verificar as Filas do Kafka
Após iniciar o projeto, você pode monitorar o tópico `pedidos-processados` com o comando abaixo:
```bash
docker exec -it kafka kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic pedidos-processados --from-beginning
```

---

## 📑 Índice
- [Pré-requisitos](#pré-requisitos)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura do Projeto](#arquitetura-do-projeto)
- [Configuração e Instalação](#configuração-e-instalação)
- [Endpoints da API](#endpoints-da-api)
- [Configuração do Kafka](#configuração-do-kafka)
- [Execução e Testes](#execução-e-testes)
- [Estrutura do Banco de Dados](#estrutura-do-banco-de-dados)
- [Contribuições](#contribuições)
- [Licença](#licença)

---

## 🛠️ Pré-requisitos

Antes de iniciar, certifique-se de ter instalado em sua máquina:
- **Java 11+**
- **Maven**
- **Docker e Docker Compose**
- **Postman** (ou similar) para testar os endpoints
- **DBeaver** (ou similar) para acessar o banco de dados PostgreSQL

---

## 🛠️ Tecnologias Utilizadas

- **Java 11**
- **Spring Boot 3.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Flyway** (para migrações de banco de dados)
- **Apache Kafka** (para mensageria)
- **Lombok**
- **Swagger** (documentação da API)
- **Logback/SLF4J** (para logging)
- **Docker e Docker Compose** (para orquestração de containers)

---

## 🏛️ Arquitetura do Projeto

Este projeto segue a **Arquitetura Hexagonal** para garantir um alto nível de desacoplamento entre os componentes. Os principais pacotes são:

```
src/main/java/com/teste/order
├── application
│   └── service          # Regras de negócio
├── domain
│   ├── model            # Entidades do domínio
│   └── repository       # Interfaces de repositórios
├── infrastructure
│   ├── adapter
│   │   └── controller   # Controladores REST
│   ├── mapper           # Mapeamento entre DTOs e entidades
│   ├── messaging        # Implementação de Kafka Producer/Consumer
│   └── repository       # Implementação de repositórios
└── dto
    ├── request          # DTOs de entrada (Request)
    └── response         # DTOs de saída (Response)
```

---

## ⚙️ Configuração e Instalação

### Variáveis de ambiente
Certifique-se de que as seguintes variáveis estejam configuradas corretamente no arquivo `application.properties`:

```properties
# Configuração do Banco de Dados
spring.datasource.url=jdbc:postgresql://localhost:5433/orderdb
spring.datasource.username=adminteste
spring.datasource.password=vS004Z

# Configuração do Kafka
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=order-group
spring.kafka.consumer.auto-offset-reset=earliest
```

---

## 📋 Endpoints da API

### 1. **Criar Pedido**
```http
POST /api/v1/pedidos
```
**Corpo da Requisição**:
```json
{
  "codigoPedido": "12345",
  "produtos": [
    {
      "codigoProduto": "P001",
      "nomeProduto": "Produto A",
      "valorUnitario": 100.0,
      "quantidade": 2
    }
  ]
}
```

### 2. **Listar Pedidos**
```http
GET /api/v1/pedidos
```

---

## 🛠️ Configuração do Kafka

```bash
docker exec -it kafka kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic pedidos-processados --from-beginning
```

---

## 🚀 Execução e Testes

Acesse a documentação do Swagger em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🗄️ Estrutura do Banco de Dados

### Rodar Migrações
```bash
mvn flyway:migrate
```

---

## 🤝 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

---

## 📄 Licença

Este projeto é licenciado sob a **MIT License**.
