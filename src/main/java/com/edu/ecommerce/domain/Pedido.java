package com.edu.ecommerce.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(nullable = false)
    private String status; // PENDENTE | ENVIADO | CANCELADO

    // FetchType.LAZY proposital — pode causar LazyInitializationException
    // se acessado fora de uma transação ativa.
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "pedido_id")
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido() {}

    public Pedido(String status) {
        this.status = status;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<ItemPedido> getItens() { return itens; }
    public void setItens(List<ItemPedido> itens) { this.itens = itens; }

    public Double getValorTotal() {
        return itens.stream().mapToDouble(ItemPedido::getSubtotal).sum();
    }
}
