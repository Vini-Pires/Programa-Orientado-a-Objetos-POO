/*
 * Introdução à POO: os exemplos da aula em um só arquivo
 *
 * Material complementar da aula "Introdução à POO e às Linguagens
 * Java e Python"
 * Disciplina: Programação Orientada a Objetos (UTFPR, Campus Apucarana)
 * Professor: Luiz Rodrigues (luizrodrigues@utfpr.edu.br)
 *
 * Observações:
 * - Este arquivo reúne as classes para facilitar o compartilhamento.
 *   Em projetos maiores, cada classe pública vive em seu próprio arquivo.
 * - Esta é a única aula que não usa o UTF Quest: aqui o objetivo é
 *   comparar Java e Python nos elementos básicos da linguagem. O jogo
 *   começa na próxima aula, com classes e objetos.
 * - As classes Conta e Pessoa são as dos slides. Conta ganhou um
 *   getSaldo() para que o programa possa mostrar o resultado; ler e
 *   escrever atributos com segurança é o tema da aula de encapsulamento.
 * - A saída dos dois arquivos é a mesma, com uma exceção: Java escreve
 *   o booleano como true e Python como True.
 * - A linha idade = "vinte" aparece comentada de propósito: em Java ela
 *   nem compila, e é isso que a tipagem estática tem a oferecer.
 *
 * Como executar (JDK 11 ou superior):
 *   java PooIntroducao.java
 */

public class PooIntroducao {

    public static void main(String[] args) {

        // Olá, mundo: em Java, todo programa vive dentro de uma classe
        System.out.println("Olá, mundo!");
        System.out.println();

        // Variáveis e tipagem: o tipo é declarado e o compilador cobra
        int idade = 20;
        double altura = 1.75;
        String nome = "Ana";
        boolean ativo = true;

        // idade = "vinte";  // ERRO de compilação!

        System.out.println(nome + ", " + idade + " anos, " + altura + " m, ativo: " + ativo);
        System.out.println();

        // Estruturas de controle: as chaves delimitam os blocos
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                System.out.println(i + " é par");
            }
        }
        System.out.println();

        // Funções em Java são métodos: vivem dentro de uma classe
        double m = media(7.0, 9.0);
        System.out.println("média: " + m);
        System.out.println();

        // Procedural vs. OO: quem sabe depositar é a própria Conta
        Conta c = new Conta();
        c.depositar(100);
        c.sacar(30);
        System.out.println("saldo: " + c.getSaldo());
        System.out.println();

        // Uma prévia da próxima aula: classe é o molde, objeto é a
        // instância
        Pessoa p = new Pessoa();
        p.nome = "Ana";
        p.idade = 20;
        p.apresentar();
    }

    public static double media(double a, double b) {
        return (a + b) / 2;
    }
}

// Orientado a objetos: dados e comportamento na mesma unidade
class Conta {

    private double saldo;

    void depositar(double v) {
        saldo += v;
    }

    void sacar(double v) {
        saldo -= v;
    }

    // Acrescentado para o programa poder exibir o resultado
    double getSaldo() {
        return saldo;
    }
}

class Pessoa {

    String nome;
    int idade;

    void apresentar() {
        System.out.println("Sou " + nome);
    }
}
