# UTF Quest: um RPG em modo texto
#
# Material complementar da aula "Polimorfismo"
# Disciplina: Programação Orientada a Objetos (UTFPR, Campus Apucarana)
# Professor: Luiz Rodrigues (luizrodrigues@utfpr.edu.br)
#
# Observações:
# - Evolução em relação à aula passada: o UTF Quest ganhou polimorfismo.
#   Em Python as variáveis não declaram tipo, então a lista mista sempre
#   funcionou; a herança é o que garante que todos na lista respondem
#   aos mesmos comandos, cada um com a sua versão (ligação dinâmica).
# - Personagem virou uma classe abstrata (ABC): no jogo, todo herói
#   pertence a uma classe concreta, e Personagem(...) agora levanta
#   TypeError. O método abstrato habilidade() obriga cada subclasse a
#   ter a sua.
# - O Chefe é a prova do código aberto para crescer: uma subclasse
#   nova, criada com os parâmetros extras do __init__ (a "sobrecarga"
#   à moda Python), e o laço da batalha não mudou uma linha.
# - isinstance aparece uma única vez, para o que é só do Mago: no dia
#   a dia, prefira sobrescrever o método.
#
# Como executar (Python 3):
#   python utf_quest_polimorfismo.py

from abc import ABC, abstractmethod


class Personagem(ABC):
  # Classe abstrata: não gera objetos; existe para as filhas herdarem

  # Parâmetros padrão fazem o papel da sobrecarga de construtores
  def __init__(self, nome, vida=100, forca=10):
    self._nome = ""
    self._vida = 100
    self._forca = 10
    self._inventario = []
    self.nome = nome      # passa pelas properties: nasce validado
    self.vida = vida
    self.forca = forca

  # Método abstrato: cada subclasse escreve o seu
  @abstractmethod
  def habilidade(self):
    ...

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

  # Parâmetro padrão faz o papel da sobrecarga de métodos
  def curar(self, qtd=10):
    self.vida = self.vida + qtd

  def esta_vivo(self):
    return self.vida > 0

  def ficha(self):
    return (self.nome + " (vida: " + str(self.vida) + ", forca: " + str(self.forca) + ")")

  # Parâmetro polimórfico: qualquer personagem serve como alvo
  def atacar(self, alvo):
    print(self.nome, "ataca", alvo.nome)
    alvo.receber_dano(self.forca)

  def pegar(self, i):
    self._inventario.append(i)
    self.forca = self.forca + i.bonus_forca
    print(self.nome, "pegou", i.nome, "(forca agora: " + str(self.forca) + ")")


class Item:

  # O Item também exige os dados essenciais para nascer
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


class Mago(Personagem):

  # Só o que é próprio do Mago; o resto vem por herança
  def __init__(self, nome):
    super().__init__(nome)   # Personagem inicializa nome, vida e forca
    self._mana = 50          # depois, a parte que é só do Mago

  @property
  def mana(self):
    return self._mana

  # Obrigação cumprida: a habilidade do Mago
  def habilidade(self):
    return "rajada arcana"

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

  def habilidade(self):
    return "escudo de aço"

  # Sobrescrita: a defesa absorve parte do dano antes de
  # delegar ao comportamento herdado do Personagem
  def receber_dano(self, dano):
    super().receber_dano(max(0, dano - self._defesa))


class Chefe(Personagem):
  # A subclasse nova entra na família sem mudar o laço da batalha

  def __init__(self, nome):
    super().__init__(nome, 300, 20)   # os parâmetros extras

  def habilidade(self):
    return "golpe devastador"


if __name__ == "__main__":

  # Em Python p aceita qualquer objeto; a ligação dinâmica garante
  # que o ficha() executado é o do Mago
  p = Mago("Elara")
  print(p.ficha())
  print()

  # Uma lista para todos os heróis: coleção polimórfica
  herois = [Mago("Elara"), Guerreiro("Bran")]

  # Um comando, vários comportamentos
  for h in herois:
    print(h.ficha())
  print()

  # isinstance descobre o tipo real...
  for h in herois:
    if isinstance(h, Mago):
      print(h.nome, "é mago!")
  # ...e aqui nenhuma conversão é necessária: Python olha o objeto
  for h in herois:
    if isinstance(h, Mago):
      print(h.mana)
  print()

  # A batalha final: o laço não sabe, nem precisa saber,
  # a classe de cada herói
  chefe = Chefe("Rei Lich")

  for h in herois:
    print(h.nome, "usa", h.habilidade())
    h.atacar(chefe)
  print()

  # O chefe revida: o receber_dano() do Guerreiro desconta a
  # defesa, mesmo com Bran tratado como um personagem qualquer
  for h in herois:
    chefe.atacar(h)
  print()

  for h in herois:
    print(h.ficha())
  print(chefe.ficha())
