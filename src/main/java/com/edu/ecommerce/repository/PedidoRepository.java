package com.edu.ecommerce.repository;

import com.edu.ecommerce.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.UUID;

// Todo (Funcionalidade A): Adicionar JpaSpecificationExecutor<Pedido>
// para habilitar filtros dinâmicos com Specification.
public interface PedidoRepository extends JpaRepository<Pedido, UUID>, JpaSpecificationExecutor<Pedido> {
}
