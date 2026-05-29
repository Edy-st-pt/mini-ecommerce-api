package com.edu.ecommerce.controller;

import com.edu.ecommerce.domain.Pedido;
import com.edu.ecommerce.dto.PedidoDTOs.AtualizarStatusDTO;
import com.edu.ecommerce.dto.PedidoDTOs.PedidoRequestDTO;
import com.edu.ecommerce.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody @Valid PedidoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.criarPedido(dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(@PathVariable UUID id,
                                                  @RequestBody @Valid AtualizarStatusDTO dto) {
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, dto.status()));
    }

    // =========================================================
    // 🚀 FUNCIONALIDADE A — TAREFA 2
    //
    // Implementar endpoint de busca com filtros dinâmicos:
    // GET /api/pedidos/filtrar?status=ENVIADO&dataInicio=2024-01-01&dataFim=2024-12-31&valorMinimo=100.0
    //
    // Requisitos:
    // - Todos os parâmetros são opcionais
    // - Usar Spring Data JPA Specification (sem SQL nativo)
    // - Criar a classe PedidoSpecification no pacote repository
    // =========================================================

    // =========================================================
    // 🚀 FUNCIONALIDADE B — TAREFA 3
    //
    // Já implementada no PedidoService após a correção do bug,
    // a mudança de status deve disparar uma notificação assíncrona.
    //
    // Requisitos:
    // - Criar NotificacaoService que imprime log simulando e-mail
    // - Usar Virtual Threads (Java 21): Executors.newVirtualThreadPerTaskExecutor()
    // - Configurar o Executor como @Bean em uma classe de configuração
    // =========================================================
}
