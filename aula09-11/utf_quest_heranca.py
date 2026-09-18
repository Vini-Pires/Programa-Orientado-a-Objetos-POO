# UTF Quest: um RPG em modo texto
#
# Material complementar da aula "Herança"
# Disciplina: Programação Orientada a Objetos (UTFPR, Campus Apucarana)
# Professor: Luiz Rodrigues (luizrodrigues@utfpr.edu.br)
#
# Observações:
# - Evolução em relação à aula passada: o UTF Quest ganhou classes de
#   herói sem copiar uma linha do Personagem. Mago e Guerreiro herdam
#   tudo com class Filha(Mae) e escrevem apenas o que é próprio deles.
# - Existe um único ficha() definido no Personagem: herdar não é copiar.
#   O Mago sobrescreve o método e chama super().ficha() para reaproveitar
#   a versão herdada e apenas completar.
# - Ordem de inicialização: a chamada explícita a super().__init__(...)
#   garante que nome, vida e forca já existem antes de a subclasse criar
#   o que é seu.
# - Seguimos com o prefixo _ e as properties: em Python a convenção
#   vale também para as subclasses, e não precisamos de protected.
# - Uma lista com magos e guerreiros já funcionaria aqui, porque Python
#   não declara tipos; o que a herança garante é que todos respondem aos
#   mesmos comandos. Esse é o tema da próxima aula: polimorfismo.
#
# Como executar (Python 3):
#   python utf_quest_heranca.py


class Personagem:

  def __init__(self, nome, vida=100, forca=10):
    self._nome = ""
    self._vida = 100
    self._forca = 10
    self._inventario = []
    self.nome = nome      # passa pelas properties: nasce validado
    self.vida = vida
    self.forca = forca

  # Properties: leitura e escrita controladas com sintaxe natural

  @property
  def nome(self):
    return self._nome

  @nome.setter
  def nome(self, n):
    if n:
      self._nome = n
    else:
      print("nome inválido")

  @property
  def vida(self):
    return self._vida

  @vida.setter
  def vida(self, v):
    if v >= 0:
      self._vida = v
    else:
      print("vida inválida:", v)

  @property
  def forca(self):
    return self._forca

  @forca.setter
  def forca(self, f):
    if 0 <= f <= 100:
      self._forca = f
    else:
      print("força inválida:", f)

  # Interface pública: as ações que o jogo pode usar

  def receber_dano(self, dano):
    self.vida = max(0, self.vida - dano)
    print(self.nome, "sofreu", dano, "de dano")

  def curar(self, qtd=10):
    self.vida = self.vida + qtd

  def esta_vivo(self):
    return self.vida > 0

  def ficha(self):
    return (self.nome + " (vida: " + str(self.vida) + ", forca: " + str(self.forca) + ")")

  def atacar(self, alvo):
    print(self.nome, "ataca", alvo.nome)
    alvo.receber_dano(self.forca)

  def pegar(self, i):
    self._inventario.append(i)
    self.forca = self.forca + i.bonus_forca
    print(self.nome, "pegou", i.nome, "(forca agora: " + str(self.forca) + ")")


class Item:

  def __init__(self, nome, bonus):
    self._nome = ""
    self._bonus_forca = 0
    self.nome = nome
    self.bonus_forca = bonus

  @property
  def nome(self):
    return self._nome

  @nome.setter
  def nome(self, n):
    self._nome = n

  @property
  def bonus_forca(self):
    return self._bonus_forca

  @bonus_forca.setter
  def bonus_forca(self, b):
    if b >= 0:
      self._bonus_forca = b
    else:
      print("bônus inválido:", b)


# Os parênteses estabelecem o parentesco: o Mago é um Personagem
class Mago(Personagem):

  # Só o que é próprio do Mago; o resto vem por herança
  def __init__(self, nome):
    super().__init__(nome)   # Personagem inicializa nome, vida e forca
    self._mana = 50          # depois, a parte que é só do Mago

  @property
  def mana(self):
    return self._mana

  # Sobrescrita: mesmo nome, mesma assinatura, corpo novo.
  # super().ficha() reaproveita a versão herdada e apenas completa.
  def ficha(self):
    return super().ficha() + " [mana: " + str(self._mana) + "]"


class Guerreiro(Personagem):

  def __init__(self, nome):
    super().__init__(nome)
    self._defesa = 5

  @property
  def defesa(self):
    return self._defesa

  # Sobrescrita: a defesa absorve parte do dano antes de
  # delegar ao comportamento herdado do Personagem
  def receber_dano(self, dano):
    super().receber_dano(max(0, dano - self._defesa))


if __name__ == "__main__":

  # A pergunta da abertura, revisitada: quanto código foi
  # copiado? Nenhum
  mago = Mago("Elara")
  bran = Guerreiro("Bran")

  # ficha() sobrescrito: a versão herdada mais a mana
  print(mago.ficha())
  # ficha() herdado, sem uma linha escrita no Guerreiro
  print(bran.ficha())
  print()

  # Sem escrever um método sequer, o Mago já faz tudo o que um
  # Personagem faz
  espada = Item("Espada de Aço", 3)
  mago.pegar(espada)
  mago.curar(25)
  print(mago.ficha())
  print()

  mago.atacar(bran)
  print(bran.ficha())
  print()

  # O receber_dano() do Guerreiro desconta a defesa antes de
  # delegar ao comportamento herdado
  bran.atacar(mago)
  print(mago.ficha())
