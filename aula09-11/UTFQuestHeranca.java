/*
* UTF Quest: um RPG em modo texto
*
* Material complementar da aula "Herança"
* Disciplina: Programação Orientada a Objetos (UTFPR, Campus Apucarana)
* Professor: Luiz Rodrigues (luizrodrigues@utfpr.edu.br)
*
* Observações:
* - Este arquivo reúne as classes para facilitar o compartilhamento.
*   Em projetos maiores, cada classe pública vive em seu próprio arquivo.
* - Evolução em relação à aula passada: o UTF Quest ganhou classes de
*   herói sem copiar uma linha do Personagem. Mago e Guerreiro herdam
*   tudo com extends e escrevem apenas o que é próprio deles.
* - Existe um único ficha() definido no Personagem: herdar não é copiar.
*   O Mago sobrescreve o método e chama super.ficha() para reaproveitar
*   a versão herdada e apenas completar.
* - Ordem de inicialização: super(...) é a primeira linha do construtor
*   da subclasse. Quando ele retorna, nome, vida e forca já existem; só
*   então a parte própria da subclasse roda.
* - Seguimos com private e setters: protected abriria mão de parte da
*   proteção conquistada na aula de encapsulamento.
* - Um Mago numa variável Personagem, e uma lista com magos e
*   guerreiros, são o tema da próxima aula: polimorfismo.
*
* Como executar (JDK 11 ou superior):
*   java UTFQuestHeranca.java
*/

import java.util.ArrayList;

public class UTFQuestHeranca {

  public static void main(String[] args) {

    // A pergunta da abertura, revisitada: quanto código foi
    // copiado? Nenhum
    Mago mago = new Mago("Elara");
    Guerreiro bran = new Guerreiro("Bran");

    // ficha() sobrescrito: a versão herdada mais a mana
    System.out.println(mago.ficha());
    // ficha() herdado, sem uma linha escrita no Guerreiro
    System.out.println(bran.ficha());
    System.out.println();

    // Sem escrever um método sequer, o Mago já faz tudo o que um
    // Personagem faz
    Item espada = new Item("Espada de Aço", 3);
    mago.pegar(espada);
    mago.curar(25);
    System.out.println(mago.ficha());
    System.out.println();

    mago.atacar(bran);
    System.out.println(bran.ficha());
    System.out.println();

    // O receberDano() do Guerreiro desconta a defesa antes de
    // delegar ao comportamento herdado
    bran.atacar(mago);
    System.out.println(mago.ficha());
  }
}

class Personagem {

  private String nome = "";
  private int vida = 100;
  private int forca = 10;
  private ArrayList<Item> inventario;

  // Construtores sobrecarregados: duas formas de nascer

  public Personagem(String nome) {
    setNome(nome);
    this.inventario = new ArrayList<>();
  }

  public Personagem(String nome, int vida, int forca) {
    this(nome);   // encadeia: chama o construtor acima
    setVida(vida);
    setForca(forca);
  }

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

  private String nome = "";
  private int bonusForca = 0;

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

// Uma linha estabelece o parentesco: o Mago é um Personagem
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

  // Sobrescrita: a defesa absorve parte do dano antes de
  // delegar ao comportamento herdado do Personagem
  @Override
  void receberDano(int dano) {
    super.receberDano(Math.max(0, dano - defesa));
  }
}
