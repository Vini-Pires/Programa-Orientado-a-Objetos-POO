# UTF Quest: um RPG em modo texto
#
# Material complementar da aula "Encapsulamento e Validação de Dados"
# Disciplina: Programação Orientada a Objetos (UTFPR, Campus Apucarana)
# Professor: Luiz Rodrigues (luizrodrigues@utfpr.edu.br)
#
# Observações:
# - Evolução em relação à aula passada: os atributos ganharam o prefixo _
#   e o acesso passa por properties. O uso continua natural
#   (heroi.vida = 80), mas sempre passa pelos métodos.
# - As regras do UTF Quest moram nos setters: vida nunca negativa,
#   força de 0 a 100, nome não vazio. Quem protege o estado é o objeto.
# - Diferença importante em relação ao Java: aqui chefe._vida = -999 roda
#   sem reclamar. O _ é um combinado entre programadores, não uma trava;
#   Java fiscaliza com o compilador.
# - Recusar com mensagem no console é provisório: a partir da aula de
#   tratamento de exceções, o setter passa a lançar o erro para quem
#   chamou. Por enquanto, o print é o que temos.
# - O personagem ainda nasce vazio e é preenchido campo a campo:
#   construtores são o tema da próxima aula.
#
# Como executar (Python 3):
#   python utf_quest_encapsulamento.py


class Personagem:

  def __init__(self):
    # Detalhes internos: o _ sinaliza uso interno
    self._nome = ""
    self._vida = 100
    self._forca = 10
    self._inventario = []

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

  def __init__(self):
    self._nome = ""
    self._bonus_forca = 0

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


if __name__ == "__main__":

  heroi = Personagem()
  heroi.nome = "Aria"
  heroi.forca = 12

  chefe = Personagem()
  chefe.nome = "Dragão de Lava"

  # A sabotagem da abertura da aula, agora recusada pela property
  chefe.vida = -999          # vida inválida: -999

  print(chefe.ficha())
  print()

  # A property é o único caminho de escrita; e ela aceita o válido
  chefe.vida = 20
  print(chefe.ficha())
  print()

  # A leitura também passa pela property
  v = heroi.vida
  print("vida do herói: " + str(v))
  print()

  espada = Item()
  espada.nome = "Espada de Aço"
  espada.bonus_forca = 3
  heroi.pegar(espada)
  print()

  # Até os métodos da classe passam pelo caminho validado:
  # um golpe fortíssimo derruba a vida até 0, nunca abaixo
  heroi.atacar(chefe)
  heroi.atacar(chefe)
  print()

  print(chefe.ficha())
