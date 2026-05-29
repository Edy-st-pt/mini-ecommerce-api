# 🛒 Mini E-commerce API

API RESTful desenvolvida com **Java 21 + Spring Boot 3 + PostgreSQL**.

> **Atividade prática:** corrija um bug e implemente 2 funcionalidades usando IA como ferramenta de desenvolvimento.

---

## 🛠️ Stack

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 3.2.5 |
| Spring Data JPA | — |
| PostgreSQL | 15+ |
| Maven | 3.9+ |

---

## ▶️ Como executar

### Pré-requisitos
- JDK 21
- PostgreSQL rodando localmente (ou via Docker)
- Maven instalado (ou usar o wrapper `./mvnw`)

### 1. Banco de dados

```sql
CREATE DATABASE ecommerce_db;
```

Ajuste usuário e senha em `src/main/resources/application.properties` se necessário.

### 2. Rodar a aplicação

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`. O Hibernate cria as tabelas automaticamente.

### 3. Testar os endpoints

```bash
# Listar produtos
GET http://localhost:8080/api/produtos

# Criar pedido
POST http://localhost:8080/api/pedidos
Content-Type: application/json
{
  "itens": [
    { "produtoId": "<uuid-do-produto>", "quantidade": 2 }
  ]
}

# Atualizar status (aqui está o bug!)
PATCH http://localhost:8080/api/pedidos/<uuid>/status
Content-Type: application/json
{ "status": "ENVIADO" }
```

---

## 🎯 Tarefas da Atividade

### Tarefa 1 — Corrigir o Bug (`bugfix/correcao-estoque`)

**Onde:** `PedidoService.java` → método `atualizarStatus()`

**O problema:** o método permite alterar o status para `ENVIADO` mesmo com estoque zerado, e pode lançar `LazyInitializationException` ao acessar `pedido.getItens()` fora de transação.

**O que corrigir:**
1. Adicionar `@Transactional` no método
2. Antes de salvar, verificar se cada item tem estoque suficiente
3. Se estoque OK → decrementar `quantidadeEstoque` de cada `Produto`
4. Se estoque insuficiente → lançar exceção com mensagem clara

---

### Tarefa 2 — Filtro Dinâmico (`feature/filtro-dinamico`)

**Onde:** `PedidoController.java` → endpoint `GET /api/pedidos/filtrar`

**O que implementar:**

Endpoint que aceita filtros opcionais via query params:

```
GET /api/pedidos/filtrar?status=ENVIADO&dataInicio=2024-01-01&dataFim=2024-12-31&valorMinimo=100.0
```

**Requisitos:**
- Todos os parâmetros são opcionais
- Usar `Spring Data JPA Specification` (sem SQL nativo)
- Criar classe `PedidoSpecification` no pacote `repository`
- Adicionar `JpaSpecificationExecutor<Pedido>` no `PedidoRepository`

---

### Tarefa 3 — Notificação Assíncrona com Virtual Threads (`feature/notificacao-virtual-threads`)

**Onde:** novo serviço `NotificacaoService.java`

**O que implementar:**

Após a mudança de status em `atualizarStatus()`, disparar uma notificação assíncrona simulada usando **Virtual Threads do Java 21**.

**Requisitos:**
- Criar `VirtualThreadConfig.java` com um `@Bean` do tipo `Executor` usando `Executors.newVirtualThreadPerTaskExecutor()`
- Criar `NotificacaoService.java` que injeta o `Executor` e executa a simulação
- A simulação deve: esperar 2s (`Thread.sleep(2000)`) e logar uma mensagem no console
- Chamar o serviço a partir de `PedidoService.atualizarStatus()`

---

## 📦 Entregáveis

1. **Repositório forkado** com 3 branches e PRs abertos para `main`:
   - `bugfix/correcao-estoque`
   - `feature/filtro-dinamico`
   - `feature/notificacao-virtual-threads`

2. **Relatório de Prompts** (`relatorio-prompts.md`) contendo, para cada tarefa:
   - O prompt inicial enviado à IA
   - Se precisou ajustar — o prompt de refinamento
   - O código final utilizado

---

## 🧠 Como estruturar seus prompts

Use o framework **Papel → Contexto → Tarefa → Restrições → Saída esperada**:

```
Atue como um Engenheiro Java Sênior especialista em Spring Boot 3 e Java 21.

Contexto: estou em uma API REST com Spring Data JPA e PostgreSQL.
A entidade Pedido tem uma relação OneToMany LAZY com ItemPedido.

Tarefa: o método atualizarStatus() em PedidoService precisa ser corrigido.
[COLE O CÓDIGO AQUI]

Restrições:
- Adicione @Transactional corretamente
- Valide estoque antes de marcar como ENVIADO
- Lance RuntimeException com mensagem descritiva se o estoque for insuficiente
- Siga Clean Code — lógica de negócio deve ficar na camada Service

Formato esperado: retorne apenas o método corrigido com uma explicação de 3 linhas
sobre o que foi alterado e por quê.
```

**Dicas:**
- Se o código gerado não compilar → cole o erro no próximo prompt e peça para corrigir
- Se funcionar mas estiver mal organizado → peça refatoração com princípio específico (ex: SRP)
- Seja preciso nos termos técnicos: nomes de classes, pacotes, versões

---

## 📁 Estrutura do Projeto

```
src/main/java/com/edu/ecommerce/
├── MiniEcommerceApiApplication.java
├── controller/
│   ├── PedidoController.java
│   └── ProdutoController.java
├── domain/
│   ├── Pedido.java
│   ├── ItemPedido.java
│   └── Produto.java
├── dto/
│   └── PedidoDTOs.java
├── exception/
│   └── GlobalExceptionHandler.java
├── repository/
│   ├── PedidoRepository.java
│   └── ProdutoRepository.java
└── service/
    ├── PedidoService.java
    └── ProdutoService.java
```
