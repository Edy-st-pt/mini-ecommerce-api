package com.edu.ecommerce.repository;

import com.edu.ecommerce.domain.ItemPedido;
import com.edu.ecommerce.domain.Pedido;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoSpecification {

    public static Specification<Pedido> comFiltros(String status, LocalDate dataInicio, LocalDate dataFim, Double valorMinimo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null && !status.isEmpty()) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (dataInicio != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataCriacao"), dataInicio.atStartOfDay()));
            }

            if (dataFim != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dataCriacao"), dataFim.atTime(LocalTime.MAX)));
            }

            if (valorMinimo != null) {
                Join<Pedido, ItemPedido> itens = root.join("itens", JoinType.INNER);
                query.groupBy(root.get("id"));
                query.having(cb.greaterThanOrEqualTo(
                        cb.sum(cb.prod(itens.get("precoUnitario"), itens.get("quantidade"))),
                        valorMinimo
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
