# Atividade em Sala - Fundamentos de OO

## Uma Classe que Protege o Próprio Estado

**Objetivo**: Classes, construtores e encapsulamento foram vistos em aulas separadas, mas no código real trabalham juntos: o construtor faz o objeto nascer completo e os setters impedem que ele fique inválido depois. Nesta atividade você vai projetar e implementar do zero um modelo de duas classes, em um domínio de sua escolha, reunindo três assuntos em um programa executável. O domínio não pode ser o UTF Quest usado em aula.

**Instruções Gerais:**

* Atividade Individual, com duração de 60 minutos.
* Use Java ou Python, sem interface gráfica: a demonstração é por saída no console.
* O uso de ferramentas de IA é opcional. Se utilizar, siga as orientações da aula deste assunto e registre no cabeçalho do arquivo os links das interações.

**Produto Esperado:** Código documentado no git, dentro da pasta "Atividade em Sala - Fundamentos de OO", com as duas classes, a demonstração e os comentários pedidos nos passos 1 e 3.

* O link da pasta deve ser enviado no Moodle!

**Confira os critérios antes de enviar:**

1. Duas classes, uma delas usando o objeto da outra.
2. Atributos fechados (private em Java, _ em Python), acessados por getters e setters ou properties.
3. Construtor que exige os dados indispensáveis e reaproveita os setters para validar.
4. Duas formas de criar o objeto: sobrecarga em Java ou parâmetros padrão em Python.
5. Pelo menos duas regras de validação que recusem valores inválidos.
6. A demonstração roda e mostra uma criação válida, uma recusa e as duas formas de criação.

**Passos:**

1. Plano (5 minutos). No cabeçalho do arquivo, em comentário: o domínio, as duas classes e seus atributos, as duas validações pretendidas e o tempo previsto para cada parte. Se pretende usar IA, escreva para quê.
2. Mão na massa (50 minutos). Implemente as classes e, por último, a demonstração. Aos 25 minutos, pare e compare o progresso com o plano: se estiver atrasado, reduza o escopo em vez de abandonar um critério, e registre o ajuste em um comentário.
3. Autoavaliação (5 minutos). Execute o programa, confira a saída contra o critério 6 e escreva no fim do arquivo: quais critérios atingiu e quais não; qual trecho deu mais trabalho e como resolveu; se usou IA, em que ela ajudou e em que atrapalhou.
4. Faça o envio antes do fim do tempo.

**Discussão em Turma** (até 10 minutos):

* Que domínios apareceram e em quais deles a validação ficou mais natural?
* Quem precisou reduzir o escopo aos 25 minutos? O que tornou o plano otimista?
* Qual validação parecia óbvia no papel e deu trabalho no código?
* Onde a sobrecarga, ou parâmetro padrão, ajudou de fato e onde só aumentou o código?
* Para quem usou IA: quando ela poupou tempo e quando gerou código só aparentemente correto?