package com.edu.ecommerce.service;

import com.edu.ecommerce.domain.Produto;
import com.edu.ecommerce.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(UUID id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
}
