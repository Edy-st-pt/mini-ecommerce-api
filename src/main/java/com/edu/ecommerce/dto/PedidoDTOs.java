package com.edu.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class PedidoDTOs {

    public record PedidoRequestDTO(
        @NotEmpty(message = "O pedido deve conter pelo menos um item")
        List<ItemRequestDTO> itens
    ) {}

    public record ItemRequestDTO(
        @NotNull(message = "Id do produto é obrigatório")
        UUID produtoId,

        @NotNull(message = "Quantidade é obrigatória")
        @Min(value = 1, message = "A quantidade mínima é 1")
        Integer quantidade
    ) {}

    public record AtualizarStatusDTO(
        @NotNull(message = "O novo status é obrigatório")
        String status
    ) {}
}
