class ItemPedido:
	def __init__(self, nome, preco_unitario, quantidade=1):
		# O construtor repassa os valores para os setters, 
		# garantindo que a validação seja feita já no nascimento do objeto.
		self.nome = nome
		self.preco_unitario = preco_unitario
		self.quantidade = quantidade

	@property
	def nome(self):
		return self._nome

	@nome.setter
	def nome(self, nome):
		if nome:
			self._nome = nome
		else:
			print("O nome não pode ser vazio.")

	@property
	def preco_unitario(self):
		return self._preco_unitario

	@preco_unitario.setter
	def preco_unitario(self, valor):
		if valor < 0:
			print("O preço unitário não pode ser negativo.")
		self._preco_unitario = valor

	@property
	def quantidade(self):
		return self._quantidade

	@quantidade.setter
	def quantidade(self, valor):
		if valor <= 0:
			print("A quantidade deve ser estritamente maior que zero.")
		self._quantidade = valor

	def subtotal(self):
		return self.quantidade * self.preco_unitario

	def obter_resumo(self):
		return f"{self.nome} (Qtd: {self.quantidade} x R$ {self.preco_unitario:.2f}) - Subtotal: R$ {self.subtotal():.2f}"
