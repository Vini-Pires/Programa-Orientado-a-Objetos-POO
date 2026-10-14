# UTF Quest: Singleton e Factory Method
#
# Material complementar da aula "Padrões de Projeto, Introdução"
# Disciplina: Programação Orientada a Objetos (UTFPR, Campus Apucarana)
# Professor: Luiz Rodrigues (luizrodrigues@utfpr.edu.br)
#
# Observações:
# - Evolução em relação às aulas passadas: o UTF Quest ganhou um estado
#   central (a classe Jogo) e um único lugar responsável por criar
#   personagens (a FabricaPersonagem).
# - Singleton: get_instance() devolve sempre o mesmo objeto. Em Python,
#   como no encapsulamento, o underscore em _instancia pede disciplina no
#   lugar de trancar: Jogo() continua funcionando, e o combinado é não
#   chamar.
# - Factory Method: o if que escolhe a classe existe uma vez só. Quem
#   chama recebe um personagem e não fica sabendo qual classe nasceu.
# - A demonstração roda no console para o programa ser executável em
#   qualquer ambiente. Numa tela, as mesmas três linhas ficariam dentro
#   do try do ouvinte da aula passada: a fábrica cria, o singleton
#   guarda e o except traduz o "Tipo desconhecido" em diálogo de erro.
#
# Como executar (Python 3):
#   python utf_quest_padroes.py

from abc import ABC, abstractmethod


# As classes do domínio, sem alteração desde a aula de exceções
class Personagem(ABC):

    def __init__(self, nome, vida=100, forca=10):
        self._nome = ""
        self._vida = 100
        self._forca = 10
        self.nome = nome
        self.vida = vida
        self.forca = forca

    @abstractmethod
    def habilidade(self):
        ...

    @property
    def nome(self):
        return self._nome

    @nome.setter
    def nome(self, n):
        if not n:
            raise ValueError("nome inválido")
        self._nome = n

    @property
    def vida(self):
        return self._vida

    @vida.setter
    def vida(self, v):
        if v < 0:
            raise ValueError(f"vida inválida: {v}")
        self._vida = v

    @property
    def forca(self):
        return self._forca

    @forca.setter
    def forca(self, f):
        if f < 0 or f > 100:
            raise ValueError(f"força inválida: {f}")
        self._forca = f

    def ficha(self):
        return (self.nome + " (vida: " + str(self.vida)
                + ", forca: " + str(self.forca) + ")")


class Mago(Personagem):

    def __init__(self, nome):
        super().__init__(nome)
        self._mana = 50

    @property
    def mana(self):
        return self._mana

    def habilidade(self):
        return "rajada arcana"

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


# Singleton: a própria classe guarda e entrega a única instância
class Jogo:

    _instancia = None

    def __init__(self):
        self.heroi = None
        self.fase = 1

    @classmethod
    def get_instance(cls):
        if cls._instancia is None:
            cls._instancia = Jogo()
        return cls._instancia


# Factory Method: a criação concentrada em um só lugar
class FabricaPersonagem:

    @staticmethod
    def criar(tipo, nome):
        if tipo == "Guerreiro":
            return Guerreiro(nome)
        if tipo == "Mago":
            return Mago(nome)
        raise ValueError("Tipo desconhecido: " + tipo)


if __name__ == "__main__":

    # Factory Method: quem pede não conhece as classes concretas
    heroi = FabricaPersonagem.criar("Guerreiro", "Bran")
    print(heroi.ficha())
    print(heroi.nome, "usa", heroi.habilidade())
    print()

    # O singleton guarda o estado central do jogo
    Jogo.get_instance().heroi = heroi

    # Outra parte do programa, sem passar a referência adiante:
    # é o mesmo objeto Jogo
    mesmo_heroi = Jogo.get_instance().heroi
    print("herói no jogo: " + mesmo_heroi.ficha())
    print("get_instance() devolveu a mesma instância"
          if Jogo.get_instance() is Jogo.get_instance()
          else "instâncias diferentes!")
    print()

    # Um tipo novo não muda quem chama a fábrica
    mago = FabricaPersonagem.criar("Mago", "Elara")
    print(mago.ficha())
    print(mago.nome, "usa", mago.habilidade())
    print()

    # O tipo desconhecido continua sendo um erro do domínio,
    # tratado como na aula de exceções
    try:
        FabricaPersonagem.criar("Arqueiro", "Lia")
    except ValueError as e:
        print("erro:", e)