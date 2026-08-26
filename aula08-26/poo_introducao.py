'''
    Introdução à POO: os exemplos da aula em um só arquivo

    Material complementar da aula "Introdução à POO e às Linguagens
    Java e Python"
    Disciplina: Programação Orientada a Objetos (UTFPR, Campus Apucarana)
    Professor: Luiz Rodrigues (luizrodrigues@utfpr.edu.br)

    Observações:
    - Esta é a única aula que não usa o UTF Quest: aqui o objetivo é
    comparar Java e Python nos elementos básicos da linguagem. O jogo
    começa na próxima aula, com classes e objetos.
    - As classes Conta e Pessoa são as dos slides. Conta ganhou um
    get_saldo() para que o programa possa mostrar o resultado; ler e
    escrever atributos com segurança é o tema da aula de encapsulamento.
    - A saída dos dois arquivos é a mesma, com uma exceção: Java escreve
    o booleano como true e Python como True.
    - A linha idade = "vinte" aparece comentada aqui só para manter os
    dois arquivos lado a lado: em Python ela é aceita sem reclamação.

    Como executar (Python 3):
        python poo_introducao.py
'''

# Orientado a objetos: dados e comportamento na mesma unidade
class Conta:

    def __init__(self):
        self._saldo = 0.0

    def depositar(self, v):
        self._saldo += v

    def sacar(self, v):
        self._saldo -= v

    # Acrescentado para o programa poder exibir o resultado
    def get_saldo(self):
        return self._saldo


class Pessoa:

    def __init__(self, nome, idade):
        self.nome = nome
        self.idade = idade

    def apresentar(self):
        print("Sou", self.nome)


if __name__ == "__main__":

    # Olá, mundo: em Python, o script roda direto
    print("Olá, mundo!")
    print()

    # Variáveis e tipagem: o tipo pertence ao valor, não à variável
    idade = 20
    altura = 1.75
    nome = "Ana"
    ativo = True

    # idade = "vinte"  # aceito!

    # print(nome + ", " + str(idade) + " anos, " + str(altura) + " m, ativo: " + str(ativo))
    print(f'{nome}, {idade} anos, {altura} m, ativo: {ativo}')
    print()

    # Estruturas de controle: a indentação delimita os blocos
    for i in range(5):
        if i % 2 == 0:
            print(i, "é par")
    print()

    # Funções em Python são livres: não precisam de classe
    def media(a, b):
        return (a + b) / 2

    m = media(7.0, 9.0)
    print("média: " + str(m))
    print()

    # Procedural vs. OO: quem sabe depositar é a própria Conta
    c = Conta()
    c.depositar(100)
    c.sacar(30)
    print("saldo: " + str(c.get_saldo()))
    print()

    # Uma prévia da próxima aula: classe é o molde, objeto é a
    # instância
    p = Pessoa("Ana", 20)
    p.apresentar()
