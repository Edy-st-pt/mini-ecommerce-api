package com.edu.ecommerce.service;

import com.edu.ecommerce.domain.ItemPedido;
import com.edu.ecommerce.domain.Pedido;
import com.edu.ecommerce.domain.Produto;
import com.edu.ecommerce.dto.PedidoDTOs.ItemRequestDTO;
import com.edu.ecommerce.dto.PedidoDTOs.PedidoRequestDTO;
import com.edu.ecommerce.repository.PedidoRepository;
import com.edu.ecommerce.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(UUID id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
    }

    public Pedido criarPedido(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido("PENDENTE");

        List<ItemPedido> itens = dto.itens().stream().map(itemDto -> {
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDto.produtoId()));
            return new ItemPedido(produto, itemDto.quantidade());
        }).toList();

        pedido.setItens(itens);
        return pedidoRepository.save(pedido);
    }

    // =========================================================
    // 🐛 BUG INTENCIONAL — TAREFA 1
    //
    // Problemas presentes neste método:
    //
    // 1. Falta @Transactional: sem ele, o acesso a pedido.getItens()
    //    (relação LAZY) pode lançar LazyInitializationException.
    //
    // 2. Falta validação de estoque: o sistema permite marcar como
    //    ENVIADO mesmo que um produto esteja com estoque zerado.
    //    Ao confirmar o envio, o estoque de cada produto deve ser
    //    decrementado pela quantidade do item.
    //
    // Use IA para corrigir ambos os problemas.
    // =========================================================
    public Pedido atualizarStatus(UUID pedidoId, String novoStatus) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + pedidoId));

        pedido.setStatus(novoStatus);

        return pedidoRepository.save(pedido);
    }
}
