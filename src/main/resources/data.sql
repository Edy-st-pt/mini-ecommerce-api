-- Popular produtos iniciais para testes
INSERT INTO tb_produtos (id, nome, preco, quantidade_estoque)
VALUES
  (gen_random_uuid(), 'Smartphone Galaxy A55', 1899.90, 10),
  (gen_random_uuid(), 'Notebook Lenovo IdeaPad', 3499.00, 5),
  (gen_random_uuid(), 'Teclado Mecânico Redragon', 299.90, 0),
  (gen_random_uuid(), 'Mouse Gamer Logitech', 189.90, 20)
ON CONFLICT DO NOTHING;
