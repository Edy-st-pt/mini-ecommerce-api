package com.edu.ecommerce.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tb_itens_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private Double precoUnitario;

    public ItemPedido() {}

    public ItemPedido(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }

    public UUID getId() { return id; }
    public Produto getProduto() { return produto; }
    public Integer getQuantidade() { return quantidade; }
    public Double getPrecoUnitario() { return precoUnitario; }

    public Double getSubtotal() {
        return this.precoUnitario * this.quantidade;
    }
}
