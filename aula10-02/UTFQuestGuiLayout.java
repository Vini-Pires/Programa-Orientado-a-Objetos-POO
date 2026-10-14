/*
 * UTF Quest: a tela de criação de herói
 *
 * Material complementar da aula "Interface Gráfica com OO:
 * Layout e Componentes Visuais"
 * Disciplina: Programação Orientada a Objetos (UTFPR, Campus Apucarana)
 * Professor: Luiz Rodrigues (luizrodrigues@utfpr.edu.br)
 *
 * Observações:
 * - Este arquivo reúne as classes para facilitar o compartilhamento.
 *   Em projetos maiores, cada classe pública vive em seu próprio arquivo.
 * - Evolução em relação à aula passada: o UTF Quest saiu do console.
 *   A tela de criação de herói é uma classe do nosso domínio que
 *   estende JFrame: herança aplicada à interface gráfica.
 * - Tudo aqui é objeto: a janela, os rótulos (JLabel), as caixas de
 *   texto (JTextField), o botão (JButton), o painel (JPanel) e até os
 *   gerenciadores de layout (BorderLayout, GridLayout).
 * - O botão "Criar herói" ainda não faz nada, de propósito: a ligação
 *   entre o clique e o código é o modelo de eventos, tema da próxima
 *   aula. Lá, as exceções da aula passada vão validar o que o usuário
 *   digitar nos campos (experimente: hoje "abc" e -999 passam calados).
 *
 * Como executar (JDK 11 ou superior):
 *   java UTFQuestGuiLayout.java
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UTFQuestGuiLayout {

    public static void main(String[] args) {
        // A tela é um objeto: instanciar, configurar, exibir
        TelaCriacaoHeroi tela = new TelaCriacaoHeroi();
        tela.setVisible(true);
    }
}

// A tela vira uma classe do domínio: TelaCriacaoHeroi é um JFrame
class TelaCriacaoHeroi extends JFrame {

    // Componentes como atributos: outros métodos vão precisar deles
    private JTextField campoNome = new JTextField(15);
    private JTextField campoVida = new JTextField(15);
    private JButton botaoCriar = new JButton("Criar herói");

    public TelaCriacaoHeroi() {
        super("UTF Quest");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // BorderLayout: título ao norte, campos ao centro, botão ao sul
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Criação de Herói", JLabel.CENTER);
        add(titulo, BorderLayout.NORTH);

        // Painel em grade: rótulos e campos alinhados como numa tabela
        JPanel painel = new JPanel(new GridLayout(2, 2, 5, 5));
        painel.add(new JLabel("Nome:"));
        painel.add(campoNome);
        painel.add(new JLabel("Vida:"));
        painel.add(campoVida);

        // Única divergência em relação ao slide, que faz
        // add(painel, BorderLayout.CENTER) direto: o GridLayout estica
        // cada componente até preencher a célula e ignora o tamanho
        // preferido, e no CENTER o painel ainda recebe toda a altura que
        // sobra. O resultado são caixas de texto altas como uma caixa de
        // texto de várias linhas. Um painel intermediário com FlowLayout,
        // o padrão do JPanel, devolve ao JTextField o tamanho que ele
        // pediu em new JTextField(15).
        JPanel centro = new JPanel();
        centro.add(painel);
        add(centro, BorderLayout.CENTER);

        // O botão que ainda não faz nada: eventos na próxima aula!
        add(botaoCriar, BorderLayout.SOUTH);
    }
}
