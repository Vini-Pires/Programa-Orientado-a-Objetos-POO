from classes.Pedido import Pedido
from classes.ItemPedido import ItemPedido

def main():
	print("=== Demonstração do Sistema de Pedidos ===\n")

	# 1. Demonstração de criação válida (Forma 1: Passando todos os parâmetros)
	print("-> Criando item 1 (todos os parâmetros informados)...")
	item1 = ItemPedido("Notebook Dell", 4500.00, 2)
	print("Sucesso:", item1.obter_resumo())

	# 2. Demonstração de criação válida (Forma 2: Omitindo quantidade para usar o padrão)
	print("\n-> Criando item 2 (omitindo quantidade, assumindo padrão = 1)...")
	item2 = ItemPedido("Mouse sem Fio", 150.00)
	print("Sucesso:", item2.obter_resumo())

	# Montando o pedido e usando a Classe 2
	meu_pedido = Pedido("Maria Oliveira")
	meu_pedido.adicionar_item(item1)
	meu_pedido.adicionar_item(item2)
	
	print("\n")
	meu_pedido.exibir_resumo()

	# 3. Demonstração de recusa de estado inválido no construtor
	print("\n-> Tentando criar um item com preço negativo...")
	item_invalido = ItemPedido("Teclado", -50.00, 1)

	# 4. Demonstração de recusa de estado inválido após a criação
	print("\n-> Tentando alterar a quantidade do item 2 para 0 via setter...")
	item2.quantidade = 0
	

if __name__ == "__main__":
	main()
