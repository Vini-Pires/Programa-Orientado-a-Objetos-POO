from classes.ItemPedido import ItemPedido

class Pedido:
	def __init__(self, cliente):
		self.cliente = cliente
		self._itens = [] # Atributo fechado, iniciado vazio

	@property
	def cliente(self):
		return self._cliente

	@cliente.setter
	def cliente(self, nomeCliente):
		if nomeCliente:
			self._cliente = nomeCliente
		else:
			print("O nome do cliente não pode ser vazio.")

	def adicionar_item(self, item: ItemPedido):
		self._itens.append(item)

	def calcular_total(self):
		total = 0
		for item in self._itens:
			total += item.subtotal()
		return total

	def exibir_resumo(self):
		print(f"--- Resumo do Pedido: {self.cliente} ---")
		if not self._itens:
			print("Pedido vazio.")
		for item in self._itens:
			print(item.obter_resumo())
		print(f"Total do Pedido: R$ {self.calcular_total():.2f}")
		print("-" * 35)
