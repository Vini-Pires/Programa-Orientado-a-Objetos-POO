# UTF Quest: a tela de criação de herói
#
# Material complementar da aula "Interface Gráfica com OO:
# Layout e Componentes Visuais"
# Disciplina: Programação Orientada a Objetos (UTFPR, Campus Apucarana)
# Professor: Luiz Rodrigues (luizrodrigues@utfpr.edu.br)
#
# Observações:
# - Evolução em relação à aula passada: o UTF Quest saiu do console.
#   A tela de criação de herói é uma classe do nosso domínio que
#   estende tk.Tk: herança aplicada à interface gráfica.
# - Tudo aqui é objeto: a janela, os rótulos (Label), as caixas de
#   texto (Entry), o botão (Button) e o painel (Frame). Em Tkinter,
#   o layout é definido no próprio componente, com pack e grid.
# - O botão "Criar herói" ainda não faz nada, de propósito: a ligação
#   entre o clique e o código é o modelo de eventos, tema da próxima
#   aula. Lá, as exceções da aula passada vão validar o que o usuário
#   digitar nos campos (experimente: hoje "abc" e -999 passam calados).
#
# Como executar (Python 3):
#   python utf_quest_gui_layout.py

import tkinter as tk


class TelaCriacaoHeroi(tk.Tk):
    """A tela vira uma classe do domínio: TelaCriacaoHeroi é um Tk."""

    def __init__(self):
        super().__init__()
        self.title("UTF Quest")
        self.geometry("400x300")

        # Título no topo: pack empilha de cima para baixo
        titulo = tk.Label(self, text="Criação de Herói")
        titulo.pack(side="top")

        # Painel em grade: rótulos e campos alinhados como numa tabela
        painel = tk.Frame(self)
        rotulo_nome = tk.Label(painel, text="Nome:")
        rotulo_nome.grid(row=0, column=0, padx=5, pady=5)
        self.campo_nome = tk.Entry(painel, width=15)
        self.campo_nome.grid(row=0, column=1, padx=5, pady=5)
        rotulo_vida = tk.Label(painel, text="Vida:")
        rotulo_vida.grid(row=1, column=0, padx=5, pady=5)
        self.campo_vida = tk.Entry(painel, width=15)
        self.campo_vida.grid(row=1, column=1, padx=5, pady=5)
        painel.pack(expand=True)

        # O botão que ainda não faz nada: eventos na próxima aula!
        self.botao_criar = tk.Button(self, text="Criar herói")
        self.botao_criar.pack(side="bottom")


if __name__ == "__main__":
    # A tela é um objeto: instanciar e entregar o controle ao usuário
    tela = TelaCriacaoHeroi()
    tela.mainloop()