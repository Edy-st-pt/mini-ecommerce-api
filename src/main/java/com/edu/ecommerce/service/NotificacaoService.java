package com.edu.ecommerce.service;

import com.edu.ecommerce.domain.Pedido;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

@Service
public class NotificacaoService {

    private final Executor virtualThreadExecutor;

    public NotificacaoService(Executor virtualThreadExecutor) {
        this.virtualThreadExecutor = virtualThreadExecutor;
    }

    public void notificarMudancaStatus(Pedido pedido) {
        virtualThreadExecutor.execute(() -> {
            try {
                System.out.println("[THREAD " + Thread.currentThread().getName() + "] Iniciando simulação de notificação para o pedido " + pedido.getId() + "...");
                Thread.sleep(2000);
                System.out.println("[THREAD " + Thread.currentThread().getName() + "] Notificação: O status do pedido " + pedido.getId() + " foi alterado para " + pedido.getStatus() + ".");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Erro ao simular notificação: " + e.getMessage());
            }
        });
    }
}
