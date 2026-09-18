/*
* UTF Quest: um RPG em modo texto
*
* Material complementar da aula "Polimorfismo"
* Disciplina: Programação Orientada a Objetos (UTFPR, Campus Apucarana)
* Professor: Luiz Rodrigues (luizrodrigues@utfpr.edu.br)
*
* Observações:
* - Este arquivo reúne as classes para facilitar o compartilhamento.
*   Em projetos maiores, cada classe pública vive em seu próprio arquivo.
* - Evolução em relação à aula passada: o UTF Quest ganhou polimorfismo.
*   Um objeto da subclasse agora é tratado pelo tipo da superclasse
*   (upcasting), e a ligação dinâmica garante que a versão executada de
*   um método sobrescrito é sempre a do objeto real. A lista de
*   Personagem reúne toda a família, e o laço da batalha fala um só
*   comando: cada herói responde do seu jeito.
* - Personagem virou uma classe abstrata: no jogo, todo herói pertence
*   a uma classe concreta, e new Personagem(...) deixou de fazer
*   sentido. O método abstrato habilidade() obriga cada subclasse a
*   ter a sua.
* - O Chefe é a prova do código aberto para crescer: uma subclasse
*   nova, criada com o construtor de três argumentos da aula de
*   sobrecarga, e o laço da batalha não mudou uma linha.
* - instanceof e o cast (downcasting) aparecem uma única vez, para o
*   que é só do Mago: no dia a dia, prefira sobrescrever o método.
*
* Como executar (JDK 11 ou superior):
*   java UTFQuestPolimorfismo.java
*/

import java.util.ArrayList;

public class UTFQuestPolimorfismo {

  public static void main(String[] args) {

    // Upcasting: um Mago cabe numa variável Personagem.
    // Ligação dinâmica: o ficha() executado é o do Mago.
    Personagem p = new Mago("Elara");
    System.out.println(p.ficha());
    System.out.println();

    // Uma lista para todos os heróis: coleção polimórfica
    ArrayList<Personagem> herois = new ArrayList<>();
    herois.add(new Mago("Elara"));
    herois.add(new Guerreiro("Bran"));

    // Um comando, vários comportamentos
    for (Personagem h : herois) {
      System.out.println(h.ficha());
    }
    System.out.println();

    // instanceof descobre o tipo real...
    for (Personagem h : herois) {
      if (h instanceof Mago) {
        System.out.println(h.getNome() + " é mago!");
      }
    }
    // ...e o cast (downcasting) libera o que é só do Mago
    for (Personagem h : herois) {
      if (h instanceof Mago) {
        Mago m = (Mago) h;
        System.out.println(m.getMana());
      }
    }
    System.out.println();

    // A batalha final: o laço não sabe, nem precisa saber,
    // a classe de cada herói
    Chefe chefe = new Chefe("Rei Lich");

    for (Personagem h : herois) {
      System.out.println(h.getNome() + " usa " + h.habilidade());
      h.atacar(chefe);
    }
    System.out.println();

    // O chefe revida: o receberDano() do Guerreiro desconta a
    // defesa, mesmo com Bran visto como Personagem
    for (Personagem h : herois) {
      chefe.atacar(h);
    }
    System.out.println();

    for (Personagem h : herois) {
      System.out.println(h.ficha());
    }
    System.out.println(chefe.ficha());
  }
}

// Classe abstrata: não gera objetos; existe para as filhas herdarem
abstract class Personagem {

  // Atributos private; valores fixos inicializados na declaração
  private String nome;
  private int vida = 100;
  private int forca = 10;
  private ArrayList<Item> inventario = new ArrayList<>();

  // Construtores sobrecarregados: duas formas de nascer

  public Personagem(String nome) {
    setNome(nome);
  }

  public Personagem(String nome, int vida, int forca) {
    this(nome);   // encadeia: chama o construtor acima
    setVida(vida);
    setForca(forca);
  }

  // Método abstrato: cada subclasse escreve o seu
  abstract String habilidade();

  // Getters: leitura controlada

  public String getNome() {
    return nome;
  }

  public int getVida() {
    return vida;
  }

  public int getForca() {
    return forca;
  }

  // Setters: escrita validada; o objeto protege o próprio estado

  public void setNome(String n) {
    if (n != null && !n.isEmpty()) {
      nome = n;
    } else {
      System.out.println("nome inválido");
    }
  }

  public void setVida(int v) {
    if (v >= 0) {
      vida = v;
    } else {
      System.out.println("vida inválida: " + v);
    }
  }

  public void setForca(int f) {
    if (f >= 0 && f <= 100) {
      forca = f;
    } else {
      System.out.println("força inválida: " + f);
    }
  }

  // Interface pública: as ações que o jogo pode usar

  void receberDano(int dano) {
    setVida(Math.max(0, vida - dano));
    System.out.println(nome + " sofreu " + dano + " de dano");
  }

  // Sobrecarga de métodos: mesmo nome, assinaturas diferentes

  void curar() {
    curar(10);
  }

  void curar(int qtd) {
    setVida(vida + qtd);
  }

  boolean estaVivo() {
    return vida > 0;
  }

  String ficha() {
    return nome + " (vida: " + vida + ", forca: " + forca + ")";
  }

  // Parâmetro polimórfico: qualquer personagem serve como alvo
  void atacar(Personagem alvo) {
    System.out.println(nome + " ataca " + alvo.getNome());
    alvo.receberDano(forca);
  }

  void pegar(Item i) {
    inventario.add(i);
    setForca(forca + i.getBonusForca());
    System.out.println(nome + " pegou " + i.getNome() + " (forca agora: " + forca + ")");
  }
}

class Item {

  private String nome;
  private int bonusForca;

  // O Item também exige os dados essenciais para nascer
  Item(String nome, int bonus) {
    setNome(nome);
    setBonusForca(bonus);
  }

  public String getNome() {
    return nome;
  }

  public int getBonusForca() {
    return bonusForca;
  }

  public void setNome(String n) {
    nome = n;
  }

  public void setBonusForca(int b) {
    if (b >= 0) {
      bonusForca = b;
    } else {
      System.out.println("bônus inválido: " + b);
    }
  }
}

class Mago extends Personagem {

  // Só o que é próprio do Mago; o resto vem por herança
  private int mana;

  public Mago(String nome) {
    super(nome);      // Personagem inicializa nome, vida e forca
    this.mana = 50;   // depois, a parte que é só do Mago
  }

  public int getMana() {
    return mana;
  }

  // Obrigação cumprida: a habilidade do Mago
  @Override
  String habilidade() {
    return "rajada arcana";
  }

  // Sobrescrita: mesmo nome, mesma assinatura, corpo novo.
  // super.ficha() reaproveita a versão herdada e apenas completa.
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

  // Sobrescrita: a defesa absorve parte do dano antes de
  // delegar ao comportamento herdado do Personagem
  @Override
  void receberDano(int dano) {
    super.receberDano(Math.max(0, dano - defesa));
  }
}

// A subclasse nova entra na família sem mudar o laço da batalha
class Chefe extends Personagem {

  public Chefe(String nome) {
    super(nome, 300, 20);   // o construtor de três argumentos
  }

  @Override
  String habilidade() {
    return "golpe devastador";
  }
}