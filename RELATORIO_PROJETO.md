# Relatorio do Projeto - Sistema de Suporte Tecnico

## 1. Visao geral

Este projeto simula um sistema de atendimento de chamados de suporte tecnico de informatica.
Ele foi desenvolvido em Java, com interface grafica Swing, seguindo a arquitetura MVC.

O sistema permite:

- Cadastrar usuarios.
- Abrir chamados para usuarios cadastrados.
- Organizar chamados abertos em uma fila.
- Atender chamados na ordem de chegada.
- Fechar chamados com uma descricao de solucao e guardar o historico em uma pilha.
- Buscar chamados por ID usando uma arvore binaria.
- Visualizar resumos no dashboard.

O pacote raiz do projeto e:

```text
a3.nicolas
```

## 2. Arquitetura MVC

O projeto foi dividido em tres responsabilidades principais:

### Model

Pacote:

```text
src/a3/nicolas/model
```

O Model representa os dados do sistema. Ele nao conhece tela, botao, tabela ou regra visual.

Classes principais:

- `Usuario`: representa a pessoa que abre ou possui um chamado.
- `Chamado`: representa o problema tecnico registrado no sistema, incluindo a descricao de abertura e a descricao de fechamento.
- `Prioridade`: enum com os valores `BAIXA`, `MEDIA` e `ALTA`.
- `StatusChamado`: enum com os valores `ABERTO`, `EM_ATENDIMENTO` e `FECHADO`.

### Controller

Pacote:

```text
src/a3/nicolas/controller
```

O Controller recebe os pedidos da View, manipula as estruturas de dados e devolve dados prontos para exibicao.

Classes principais:

- `UsuarioController`: cadastra, lista, busca e remove usuarios.
- `ChamadoController`: abre chamados, atende o proximo, fecha chamados, lista fila, lista historico e busca por ID.

### View

Pacote:

```text
src/a3/nicolas/view
```

A View possui a interface grafica Swing. Ela captura cliques, le campos de texto e chama os controllers.
A View nao acessa diretamente Fila, Pilha, ListaLigada ou ArvoreBinaria.

Classes principais:

- `MainFrame`: janela principal com menu lateral e troca de telas usando `CardLayout`.
- `PainelDashboard`: mostra resumo do sistema.
- `PainelChamados`: abre chamados e mostra a fila.
- `PainelAtendimento`: atende e fecha chamados.
- `PainelHistorico`: mostra chamados fechados.
- `PainelUsuarios`: cadastra, lista e remove usuarios.
- `PainelBusca`: busca chamados por ID.

## 3. Estruturas de dados implementadas manualmente

Pacote:

```text
src/a3/nicolas/estruturas
```

As estruturas foram feitas manualmente com nos internos. Nao foram usadas `ArrayList`, `LinkedList`, `HashMap` ou colecoes prontas do Java dentro das estruturas.

### Fila

Arquivo:

```text
Fila.java
```

A fila segue a regra FIFO: First In, First Out.

Traduzindo: o primeiro chamado que entra e o primeiro chamado que sai.

Uso no sistema:

- Quando um chamado e aberto, ele entra na fila com `enqueue`.
- Quando o atendimento comeca, ele sai da fila com `dequeue`.

Metodos principais:

- `enqueue(T valor)`: adiciona no final da fila.
- `dequeue()`: remove e retorna o primeiro da fila.
- `peek()`: consulta o primeiro sem remover.
- `isEmpty()`: verifica se a fila esta vazia.
- `tamanho()`: retorna a quantidade de elementos.
- `listar()`: retorna os elementos da fila em ordem.

### Pilha

Arquivo:

```text
Pilha.java
```

A pilha segue a regra LIFO: Last In, First Out.

Traduzindo: o ultimo chamado fechado fica no topo e aparece primeiro no historico.

Uso no sistema:

- Quando um chamado e fechado, ele entra no historico com `push`.
- O historico e exibido do mais recente para o mais antigo.

Metodos principais:

- `push(T valor)`: adiciona no topo.
- `pop()`: remove e retorna o topo.
- `peek()`: consulta o topo sem remover.
- `isEmpty()`: verifica se a pilha esta vazia.
- `tamanho()`: retorna a quantidade de elementos.
- `listar()`: retorna os elementos do topo para a base.

### Lista Ligada

Arquivo:

```text
ListaLigada.java
```

A lista ligada guarda elementos em nos, onde cada no aponta para o proximo.

Uso no sistema:

- Armazena todos os usuarios cadastrados.
- Permite adicionar, remover, buscar e listar usuarios.

Metodos principais:

- `adicionar(T valor)`: adiciona no final da lista.
- `remover(T valor)`: remove um elemento da lista.
- `buscar(T valor)`: procura um elemento usando `equals`.
- `listar()`: retorna todos os elementos.
- `tamanho()`: retorna a quantidade de elementos.

### Arvore Binaria

Arquivo:

```text
ArvoreBinaria.java
```

A arvore binaria organiza os chamados pelo ID.

Regra:

- IDs menores ficam a esquerda.
- IDs maiores ficam a direita.

Uso no sistema:

- Todo chamado criado e inserido na arvore.
- A tela de busca usa a arvore para procurar rapidamente por ID.
- A listagem em ordem mostra os chamados ordenados pelo ID.

Metodos principais:

- `inserir(Chamado chamado)`: adiciona o chamado na arvore.
- `buscar(int id)`: procura um chamado pelo ID.
- `listarEmOrdem()`: retorna os chamados ordenados por ID.
- `tamanho()`: retorna a quantidade de chamados cadastrados na arvore.

## 4. Funcionalidades do sistema

### 4.1 Cadastro de usuarios

Tela:

```text
Usuarios
```

O usuario informa:

- Nome.
- Setor.
- Equipamento.

Quando clica em `Cadastrar`, a View chama:

```java
usuarioController.cadastrarUsuario(nome, setor, equipamento);
```

O controller cria um objeto `Usuario`, atribui um ID automatico e adiciona na `ListaLigada`.

Tambem existe a opcao `Remover selecionado`, que remove da lista o usuario selecionado na tabela.

### 4.2 Abertura de chamados

Tela:

```text
Chamados
```

Para abrir um chamado, e necessario:

- Ter pelo menos um usuario cadastrado.
- Selecionar um usuario.
- Selecionar a prioridade.
- Escrever a descricao do problema.

Quando clica em `Abrir chamado`, a View chama:

```java
chamadoController.abrirChamado(descricao, prioridade, usuario);
```

O controller:

1. Cria um `Chamado` com ID automatico.
2. Define o status inicial como `ABERTO`.
3. Registra a data e hora de abertura.
4. Coloca o chamado na `Fila`.
5. Insere o chamado na `ArvoreBinaria`.

Assim, o chamado aparece na tabela da fila e tambem fica disponivel para busca por ID.

### 4.3 Atendimento de chamados

Tela:

```text
Atender
```

O botao `Atender proximo` chama:

```java
chamadoController.atenderProximo();
```

O controller remove o primeiro chamado da fila usando `dequeue` e altera o status para `EM_ATENDIMENTO`.

Depois disso, os dados do chamado aparecem na tela:

- ID.
- Status.
- Prioridade.
- Usuario.
- Setor.
- Equipamento.
- Data de abertura.
- Descricao de abertura.
- Descricao de fechamento, quando existir.

Foi adicionada uma protecao importante: se ja existe um chamado em atendimento, o sistema nao permite atender outro antes de fechar o atual. Isso evita que um chamado fique perdido fora da fila e fora do historico.

### 4.4 Fechamento de chamados

Tela:

```text
Atender
```

Antes de fechar, o tecnico precisa preencher o campo `Descricao do fechamento`.
Esse campo representa a solucao aplicada, a observacao final ou a justificativa do encerramento, parecido com sistemas como Jira.

O botao `Fechar chamado` chama:

```java
chamadoController.fecharChamado(chamadoAtual, descricaoFechamento);
```

O controller:

1. Verifica se o chamado esta em atendimento.
2. Verifica se a descricao de fechamento foi informada.
3. Salva a descricao de fechamento no chamado.
4. Altera o status para `FECHADO`.
5. Adiciona o chamado na `Pilha` de historico.
6. Limpa o chamado que estava em atendimento.

Essa regra impede que o mesmo chamado seja fechado duas vezes, evita historico sem solucao registrada e reduz erros durante a demonstracao.

### 4.5 Historico de chamados

Tela:

```text
Historico
```

Mostra os chamados fechados.

A ordem vem da `Pilha`, entao o chamado fechado mais recentemente aparece primeiro.
Na tabela tambem aparece a descricao de fechamento, permitindo explicar qual solucao foi aplicada.

O painel chama:

```java
chamadoController.listarHistorico();
```

### 4.6 Busca por ID

Tela:

```text
Busca
```

O usuario digita o ID de um chamado e clica em `Buscar`.

A View chama:

```java
chamadoController.buscarPorId(id);
```

O controller consulta a `ArvoreBinaria`.

Se encontrar, a tela mostra:

- ID.
- Status.
- Prioridade.
- Usuario.
- Data.
- Descricao de abertura.
- Descricao de fechamento, se o chamado ja foi encerrado.

A mesma tela tambem exibe uma tabela com todos os chamados ordenados por ID, usando:

```java
chamadoController.listarTodos();
```

### 4.7 Dashboard

Tela:

```text
Dashboard
```

Mostra um resumo rapido:

- Quantidade de chamados na fila.
- Quantidade de chamados no historico.
- Ultimo chamado fechado.

O painel consulta o `ChamadoController` e atualiza os dados sempre que alguma acao importante acontece.
O card de ultimo atendido so muda depois que um chamado e fechado, para nao mostrar um chamado ainda em andamento como se ja tivesse sido concluido.

## 5. Fluxo principal do sistema

O fluxo completo esperado e:

1. Cadastrar um usuario.
2. Abrir um chamado para esse usuario.
3. Ver o chamado aparecer na fila.
4. Ir para a tela `Atender`.
5. Clicar em `Atender proximo`.
6. Conferir os dados do chamado.
7. Preencher a descricao de fechamento com a solucao aplicada.
8. Clicar em `Fechar chamado`.
9. Ver o chamado sair da fila e entrar no historico.
10. Buscar o chamado pelo ID na tela `Busca`.

## 6. Como executar

Compilar:

```powershell
javac -d out src\a3\nicolas\*.java src\a3\nicolas\model\*.java src\a3\nicolas\model\enums\*.java src\a3\nicolas\estruturas\*.java src\a3\nicolas\controller\*.java src\a3\nicolas\view\*.java src\a3\nicolas\view\panels\*.java
```

Executar:

```powershell
java -cp out a3.nicolas.Main
```

Classe principal:

```text
src/a3/nicolas/Main.java
```

### Dados de demonstracao

Ao iniciar o sistema, o `Main.java` ja carrega alguns dados para facilitar a apresentacao:

- Ana Lima, do RH, com Notebook Dell.
- Carlos Souza, da TI, com Desktop HP.
- Mariana Costa, do Financeiro, com Impressora Canon.

Tambem sao abertos tres chamados iniciais:

- Computador nao liga, prioridade `ALTA`.
- Email nao envia anexo, prioridade `MEDIA`.
- Impressora offline, prioridade `BAIXA`.

Esses dados ficam apenas em memoria. Eles servem para a demo ja comecar com a fila preenchida.

Para verificar warnings de serializacao nas telas Swing, tambem foi usada esta compilacao:

```powershell
javac -Xlint:serial -d out src\a3\nicolas\*.java src\a3\nicolas\model\*.java src\a3\nicolas\model\enums\*.java src\a3\nicolas\estruturas\*.java src\a3\nicolas\controller\*.java src\a3\nicolas\view\*.java src\a3\nicolas\view\panels\*.java
```

## 7. Pontos importantes para a apresentacao

### Por que MVC?

MVC separa responsabilidades.

- Model guarda dados.
- Controller controla regras.
- View mostra telas e recebe interacoes.

Isso deixa o projeto mais organizado e facilita explicar onde cada coisa acontece.

### Onde cada estrutura aparece?

- Fila: chamados abertos esperando atendimento.
- Pilha: historico de chamados fechados.
- Lista Ligada: usuarios cadastrados.
- Arvore Binaria: busca de chamados por ID.

### Por que nao usar banco de dados?

O enunciado pediu que tudo ficasse em memoria. Por isso, ao fechar o sistema, os dados cadastrados sao perdidos.

### Por que os IDs sao automaticos?

Para evitar IDs repetidos e facilitar a demonstracao. Cada controller tem uma variavel `proximoId`, que aumenta a cada cadastro.

### O que acontece quando um chamado muda de status?

- Ao abrir: `ABERTO`.
- Ao atender: `EM_ATENDIMENTO`.
- Ao fechar: `FECHADO`, com descricao de fechamento preenchida.

## 8. Correcoes feitas na revisao

Durante a revisao foram feitas correcoes para deixar o projeto mais seguro para a apresentacao.

### Atendimento

- Era possivel clicar em `Atender proximo` novamente antes de fechar o chamado atual.
- Isso poderia deixar o primeiro chamado em `EM_ATENDIMENTO`, mas fora da fila e fora do historico.
- O `ChamadoController` agora guarda o `chamadoEmAtendimento`.
- O sistema nao permite iniciar outro atendimento enquanto existe um chamado atual.
- O metodo `fecharChamado` so fecha o chamado que realmente esta em atendimento e que recebeu uma descricao de fechamento.
- Depois de fechar, o chamado vai para a pilha de historico e o atendimento atual e liberado.

### Dashboard

- O `ultimoAtendido` deixou de ser atualizado ao clicar em `Atender proximo`.
- Agora ele e atualizado apenas no fechamento do chamado.
- Assim, o Dashboard nao mostra um chamado em andamento como se ja estivesse concluido.

### Interface

- O menu lateral foi ajustado de `GridLayout(8, 1)` para `GridLayout(7, 1)`, removendo uma celula vazia.
- Os botoes do menu ganharam fundo escuro, texto branco e cursor de mao.
- A tela de busca ganhou o rotulo `Todos os chamados ordenados por ID (Arvore Binaria)`.
- A tela de atendimento ganhou o campo `Descricao do fechamento`.
- Ao fechar um chamado, a tela mostra uma mensagem de sucesso.

### Warnings de compilacao

- As classes Swing receberam `serialVersionUID`.
- Os campos de controller dentro das telas foram marcados como `transient`, pois a aplicacao nao serializa as telas.
- A compilacao com `-Xlint:serial` passou sem warnings.

## 9. Conclusao

O sistema atende ao objetivo proposto: simular o atendimento de chamados de suporte tecnico usando Java, Swing, MVC e estruturas de dados manuais.

Ele demonstra na pratica:

- Encapsulamento com classes de modelo.
- Uso de enums para valores fixos.
- Separacao de responsabilidades com MVC.
- Implementacao manual de fila, pilha, lista ligada e arvore binaria.
- Integracao das estruturas com uma interface grafica funcional.
