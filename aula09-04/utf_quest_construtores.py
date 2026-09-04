# UTF Quest: um RPG em modo texto
#
# Material complementar da aula "Construtores, Inicialização e
# Sobrecarga"
# Disciplina: Programação Orientada a Objetos (UTFPR, Campus Apucarana)
# Professor: Luiz Rodrigues (luizrodrigues@utfpr.edu.br)
#
# Observações:
# - Evolução em relação à aula passada: o personagem não nasce mais
#   vazio. O __init__ exige o nome, e a fila de atribuições depois da
#   criação deixou de ser necessária.
# - O __init__ atribui pelas properties (self.nome = nome), então as
#   regras de validação da aula passada valem também no nascimento.
# - Python não tem sobrecarga: um único __init__ com parâmetros padrão
#   cobre os casos que em Java exigem duas assinaturas. Definir o mesmo
#   nome duas vezes aqui apenas apaga a definição anterior.
# - Em Python não há escolha de onde inicializar: valores fixos e
#   valores vindos de parâmetro vivem os dois dentro do __init__.
#
# Como executar (Python 3):
#   python utf_quest_construtores.py


class Personagem:

    # Parâmetros padrão fazem o papel da sobrecarga de construtores
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

    # Parâmetro padrão faz o papel da sobrecarga de métodos
    def curar(self, qtd=10):
        self.vida = self.vida + qtd

    def esta_vivo(self):
        return self.vida > 0

    def ficha(self):
        return (self.nome + " (vida: " + str(self.vida)
                + ", forca: " + str(self.forca) + ")")

    def atacar(self, alvo):
        print(self.nome, "ataca", alvo.nome)
        alvo.receber_dano(self.forca)

    def pegar(self, i):
        self._inventario.append(i)
        self.forca = self.forca + i.bonus_forca
        print(self.nome, "pegou", i.nome,
              "(forca agora: " + str(self.forca) + ")")


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


if __name__ == "__main__":

    # O objeto já nasce completo: uma linha em vez de uma fila
    # de atribuições
    heroi = Personagem("Aria")
    heroi.forca = 12

    # Os parâmetros extras: a ficha completa de uma vez
    chefe = Personagem("Dragão de Lava", vida=20)

    print(heroi.ficha())
    print(chefe.ficha())
    print()

    # Sem nome não há personagem: o __init__ fecha a porta que as
    # atribuições diretas deixavam aberta
    # Personagem()            # TypeError: exige o nome
    p = Personagem("")        # nome inválido
    print(p.ficha())
    print()

    # O Item também nasce completo e validado
    espada = Item("Espada de Aço", 3)
    heroi.pegar(espada)
    print()

    # Um parâmetro padrão no lugar de duas assinaturas
    heroi.receber_dano(40)
    heroi.curar()         # +10 de vida
    print(heroi.ficha())
    heroi.curar(25)       # +25 de vida
    print(heroi.ficha())
    print()

    heroi.atacar(chefe)
    print(chefe.ficha())
