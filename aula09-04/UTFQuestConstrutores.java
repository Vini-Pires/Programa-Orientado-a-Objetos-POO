/*
* UTF Quest: um RPG em modo texto
*
* Material complementar da aula "Construtores, Inicialização e
* Sobrecarga"
* Disciplina: Programação Orientada a Objetos (UTFPR, Campus Apucarana)
* Professor: Luiz Rodrigues (luizrodrigues@utfpr.edu.br)
*
* Observações:
* - Este arquivo reúne as classes para facilitar o compartilhamento.
*   Em projetos maiores, cada classe pública vive em seu próprio arquivo.
* - Evolução em relação à aula passada: o personagem não nasce mais
*   vazio. O construtor exige o nome, e a fila de setters depois do new
*   deixou de ser necessária.
* - Os construtores reaproveitam os setters da aula de encapsulamento:
*   as regras de validação continuam num lugar só e valem também no
*   nascimento do objeto.
* - Valores fixos, iguais para todo objeto, ficam na declaração do
*   atributo; o que depende de parâmetro fica no construtor.
* - Sobrecarga: duas assinaturas de Personagem e duas de curar. O
*   this(...) na primeira linha encadeia construtores e evita repetir a
*   lógica comum. Em Python o mesmo efeito vem de parâmetros padrão.
*
* Como executar (JDK 11 ou superior):
*   java UTFQuestConstrutores.java
*/

import java.util.ArrayList;

public class UTFQuestConstrutores {
  
  public static void main(String[] args) {
    
    // O objeto já nasce completo: uma linha em vez de uma fila
    // de setters
    Personagem heroi = new Personagem("Aria");
    heroi.setForca(12);
    
    // A versão de três argumentos: a ficha completa de uma vez
    Personagem chefe = new Personagem("Dragão de Lava", 20, 10);
    
    System.out.println(heroi.ficha());
    System.out.println(chefe.ficha());
    System.out.println();
    
    // Sem nome não há personagem: o construtor fecha a porta que
    // os setters deixavam aberta
    // new Personagem();       // ERRO: não existe versão sem o nome
    Personagem p = new Personagem("");   // nome inválido
    System.out.println(p.ficha());
    System.out.println();
    
    // O Item também nasce completo e validado
    Item espada = new Item("Espada de Aço", 3);
    heroi.pegar(espada);
    System.out.println();
    
    // Sobrecarga de métodos: mesmo nome, assinaturas diferentes
    heroi.receberDano(40);
    heroi.curar();        // +10 de vida
    System.out.println(heroi.ficha());
    heroi.curar(25);      // +25 de vida
    System.out.println(heroi.ficha());
    System.out.println();
    
    heroi.atacar(chefe);
    System.out.println(chefe.ficha());
  }
}

class Personagem {
  
  // Valores fixos, iguais para todo objeto: na declaração
  private String nome = "";
  private int vida = 100;
  private int forca = 10;
  private ArrayList<Item> inventario;
  
  // Construtores sobrecarregados: duas formas de nascer
  
  public Personagem(String nome) {
    setNome(nome);                        // reaproveita a validação
    this.inventario = new ArrayList<>();  // depende do objeto
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
  
  // Sobrecarga de métodos: a versão sem argumento chama a outra
  
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
    System.out.println(nome + " pegou " + i.getNome()
    + " (forca agora: " + forca + ")");
  }
}

class Item {
  
  private String nome = "";
  private int bonusForca = 0;
  
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
