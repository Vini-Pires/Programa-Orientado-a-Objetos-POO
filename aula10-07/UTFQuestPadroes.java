/*
 * UTF Quest: Singleton e Factory Method
 *
 * Material complementar da aula "Padrões de Projeto, Introdução"
 * Disciplina: Programação Orientada a Objetos (UTFPR, Campus Apucarana)
 * Professor: Luiz Rodrigues (luizrodrigues@utfpr.edu.br)
 *
 * Observações:
 * - Este arquivo reúne as classes para facilitar o compartilhamento.
 *   Em projetos maiores, cada classe pública vive em seu próprio arquivo.
 * - Evolução em relação às aulas passadas: o UTF Quest ganhou um estado
 *   central (a classe Jogo) e um único lugar responsável por criar
 *   personagens (a FabricaPersonagem).
 * - Singleton: o construtor privado bloqueia o new de fora, e
 *   getInstance() devolve sempre o mesmo objeto. A unicidade deixa de
 *   ser convenção e passa a ser imposta pela própria classe.
 * - Factory Method: o if que escolhe a subclasse existe uma vez só. O
 *   retorno é Personagem, o tipo genérico, então quem chama nem fica
 *   sabendo qual subclasse nasceu: o polimorfismo da aula de
 *   polimorfismo, agora com nome de padrão.
 * - A demonstração roda no console para o programa ser executável em
 *   qualquer ambiente. Numa tela, as mesmas três linhas ficariam dentro
 *   do try do ouvinte da aula passada: a fábrica cria, o singleton
 *   guarda e o catch traduz o "Tipo desconhecido" em diálogo de erro.
 *
 * Como executar (JDK 11 ou superior):
 *   java UTFQuestPadroes.java
 */

public class UTFQuestPadroes {

    public static void main(String[] args) {

        // Factory Method: quem pede não conhece as subclasses
        Personagem heroi = FabricaPersonagem.criar("Guerreiro", "Bran");
        System.out.println(heroi.ficha());
        System.out.println(heroi.getNome() + " usa " + heroi.habilidade());
        System.out.println();

        // O singleton guarda o estado central do jogo
        Jogo.getInstance().setHeroi(heroi);

        // Outra parte do programa, sem passar a referência adiante:
        // é o mesmo objeto Jogo
        Personagem mesmoHeroi = Jogo.getInstance().getHeroi();
        System.out.println("herói no jogo: " + mesmoHeroi.ficha());
        System.out.println(Jogo.getInstance() == Jogo.getInstance()
                ? "getInstance() devolveu a mesma instância"
                : "instâncias diferentes!");
        System.out.println();

        // Um tipo novo não muda quem chama a fábrica
        Personagem mago = FabricaPersonagem.criar("Mago", "Elara");
        System.out.println(mago.ficha());
        System.out.println(mago.getNome() + " usa " + mago.habilidade());
        System.out.println();

        // O tipo desconhecido continua sendo um erro do domínio,
        // tratado como na aula de exceções
        try {
            FabricaPersonagem.criar("Arqueiro", "Lia");
        } catch (IllegalArgumentException e) {
            System.out.println("erro: " + e.getMessage());
        }
    }
}

// Singleton: a própria classe guarda e entrega a única instância
class Jogo {

    private static Jogo instancia;

    private Personagem heroi;
    private int fase = 1;

    // Construtor privado: ninguém cria um Jogo de fora
    private Jogo() {
    }

    public static Jogo getInstance() {
        if (instancia == null) {
            instancia = new Jogo();
        }
        return instancia;
    }

    public Personagem getHeroi() {
        return heroi;
    }

    public void setHeroi(Personagem heroi) {
        this.heroi = heroi;
    }

    public int getFase() {
        return fase;
    }
}

// Factory Method: a criação concentrada em um só lugar
class FabricaPersonagem {

    public static Personagem criar(String tipo, String nome) {
        if (tipo.equals("Guerreiro")) {
            return new Guerreiro(nome);
        }
        if (tipo.equals("Mago")) {
            return new Mago(nome);
        }
        throw new IllegalArgumentException("Tipo desconhecido: " + tipo);
    }
}

// As classes do domínio, sem alteração desde a aula de exceções
abstract class Personagem {

    private String nome = "";
    private int vida = 100;
    private int forca = 10;

    public Personagem(String nome) {
        setNome(nome);
    }

    public Personagem(String nome, int vida, int forca) {
        this(nome);
        setVida(vida);
        setForca(forca);
    }

    abstract String habilidade();

    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getForca() {
        return forca;
    }

    public void setNome(String n) {
        if (n == null || n.isEmpty()) {
            throw new IllegalArgumentException("nome inválido");
        }
        nome = n;
    }

    public void setVida(int v) {
        if (v < 0) {
            throw new IllegalArgumentException("vida inválida: " + v);
        }
        vida = v;
    }

    public void setForca(int f) {
        if (f < 0 || f > 100) {
            throw new IllegalArgumentException("força inválida: " + f);
        }
        forca = f;
    }

    String ficha() {
        return nome + " (vida: " + vida + ", forca: " + forca + ")";
    }
}

class Mago extends Personagem {

    private int mana;

    public Mago(String nome) {
        super(nome);
        this.mana = 50;
    }

    public int getMana() {
        return mana;
    }

    @Override
    String habilidade() {
        return "rajada arcana";
    }

    @Override
    String ficha() {
        return super.ficha() + " [mana: " + mana + "]";
    }
}

class Guerreiro extends Personagem {

    private int defesa;

    public Guerreiro(String nome) {
        super(nome);
        this.defesa = 5;
    }

    public int getDefesa() {
        return defesa;
    }

    @Override
    String habilidade() {
        return "escudo de aço";
    }
}
