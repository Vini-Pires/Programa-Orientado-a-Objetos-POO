/*
* UTF Quest: um RPG em modo texto
*
* Material complementar da aula "Encapsulamento e Validação de Dados"
* Disciplina: Programação Orientada a Objetos (UTFPR, Campus Apucarana)
* Professor: Luiz Rodrigues (luizrodrigues@utfpr.edu.br)
*
* Observações:
* - Este arquivo reúne as classes para facilitar o compartilhamento.
*   Em projetos maiores, cada classe pública vive em seu próprio arquivo.
* - Evolução em relação à aula passada: os atributos viraram private e
*   o acesso passa por getters e setters. A sabotagem chefe.vida = -999
*   não compila mais, e chefe.setVida(-999) é recusada pelo setter.
* - As regras do UTF Quest moram nos setters: vida nunca negativa,
*   força de 0 a 100, nome não vazio. Quem protege o estado é o objeto.
* - Recusar com mensagem no console é provisório: a partir da aula de
*   tratamento de exceções, o setter passa a lançar o erro para quem
*   chamou. Por enquanto, o print é o que temos.
* - O personagem ainda nasce vazio e é preenchido setter a setter:
*   construtores são o tema da próxima aula.
*
* Como executar (JDK 11 ou superior):
*   java UTFQuestEncapsulamento.java
*/

import java.util.ArrayList;

public class UTFQuestEncapsulamento {
  
  public static void main(String[] args) {
    
    Personagem heroi = new Personagem();
    heroi.setNome("Aria");
    heroi.setForca(12);
    
    Personagem chefe = new Personagem();
    chefe.setNome("Dragão de Lava");
    
    // A sabotagem da abertura da aula, agora barrada:
    // chefe.vida = -999;      // ERRO: vida has private access
    chefe.setVida(-999);       // vida inválida: -999
    
    System.out.println(chefe.ficha());
    System.out.println();
    
    // O setter é o único caminho de escrita; e ele aceita o válido
    chefe.setVida(20);
    System.out.println(chefe.ficha());
    System.out.println();
    
    // O getter devolve o valor sem permitir alteração
    int v = heroi.getVida();
    System.out.println("vida do herói: " + v);
    System.out.println();
    
    Item espada = new Item();
    espada.setNome("Espada de Aço");
    espada.setBonusForca(3);
    heroi.pegar(espada);
    System.out.println();
    
    // Até os métodos da classe passam pelo caminho validado:
    // um golpe fortíssimo derruba a vida até 0, nunca abaixo
    heroi.atacar(chefe);
    heroi.atacar(chefe);
    System.out.println();
    
    System.out.println(chefe.ficha());
    System.out.println("Heroi esta vivo? " + heroi.estaVivo());
  }
}

class Personagem {
  
  // Detalhes internos: fechados para o resto do programa
  private String nome = "";
  private int vida = 100;
  private int forca = 10;
  private ArrayList<Item> inventario = new ArrayList<>();
  
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
