# Documentacao Unificada - Sistema de Suporte Tecnico de Informatica

## 1. Visao geral

Este projeto e um sistema academico desenvolvido em Java para simular o atendimento de chamados de suporte tecnico de informatica.

O sistema possui interface grafica em Swing, segue arquitetura MVC e usa estruturas de dados implementadas manualmente:

- Fila.
- Pilha.
- Lista Ligada.
- Arvore Binaria.

Pacote raiz:

```text
a3.nicolas
```

Classe principal:

```text
src/a3/nicolas/Main.java
```

## 2. Requisitos atendidos

### Desenvolvido em Java

O projeto foi feito totalmente em Java, usando recursos nativos da linguagem:

- Classes e objetos.
- Encapsulamento com atributos privados e metodos `get` e `set`.
- Enums para valores fixos.
- Swing para interface grafica.
- `LocalDateTime` para registrar data e hora de abertura dos chamados.

Nao foram usados frameworks externos nem banco de dados. Os dados ficam em memoria durante a execucao.

### Tema de suporte tecnico de informatica

O sistema representa uma rotina de suporte tecnico, em que usuarios registram problemas ligados a equipamentos ou servicos de informatica.

Exemplos usados na demonstracao:

- Computador nao liga.
- Email nao envia anexo.
- Impressora offline.

Cada usuario possui nome, setor e equipamento, permitindo relacionar o chamado ao ambiente onde o problema ocorreu.

### Cadastro ou registro de informacoes

O sistema permite cadastrar usuarios na tela `Usuarios`.

Campos cadastrados:

- Nome.
- Setor.
- Equipamento.

Como foi feito:

- A tela `PainelUsuarios` captura os dados.
- O `UsuarioController` cria um objeto `Usuario`.
- O usuario e armazenado na `ListaLigada`.
- A tabela da tela e atualizada para mostrar os registros.

### Criacao de chamados tecnicos

O sistema permite criar chamados na tela `Chamados`.

Campos usados:

- Usuario.
- Prioridade.
- Descricao do problema.

Como foi feito:

- A tela `PainelChamados` envia os dados para o `ChamadoController`.
- O controller cria um objeto `Chamado` com ID automatico.
- O chamado recebe status inicial `ABERTO`.
- A data de abertura e registrada.
- O chamado entra na `Fila`.
- O chamado tambem e inserido na `ArvoreBinaria` para busca por ID.

### Atendimento e movimentacao dos chamados

O atendimento acontece na tela `Atender`.

Fluxo:

1. O tecnico clica em `Atender proximo`.
2. O primeiro chamado da fila e removido com `dequeue`.
3. O status muda de `ABERTO` para `EM_ATENDIMENTO`.
4. O tecnico analisa os dados exibidos na tela.
5. O tecnico preenche a `Descricao do fechamento`.
6. O tecnico clica em `Fechar chamado`.
7. O status muda para `FECHADO`.
8. O chamado e enviado para a `Pilha` de historico.

Existe uma protecao para impedir que outro chamado seja atendido antes de fechar o chamado atual.

### Consulta, listagem e busca de informacoes

O sistema permite consultar informacoes em varias telas:

- `Usuarios`: lista todos os usuarios cadastrados.
- `Chamados`: lista a fila atual de chamados abertos.
- `Historico`: lista chamados fechados, do mais recente para o mais antigo.
- `Busca`: busca chamados por ID usando Arvore Binaria.
- `Dashboard`: mostra resumo da fila, historico e ultimo chamado fechado.

Na busca, tambem existe uma tabela com todos os chamados ordenados por ID.

### Funcionamento correto na demonstracao

Para facilitar a apresentacao, o sistema ja inicia com dados de demonstracao:

- Ana Lima, do RH, com Notebook Dell.
- Carlos Souza, da TI, com Desktop HP.
- Mariana Costa, do Financeiro, com Impressora Canon.

Tambem sao criados tres chamados iniciais:

- Computador nao liga, prioridade `ALTA`.
- Email nao envia anexo, prioridade `MEDIA`.
- Impressora offline, prioridade `BAIXA`.

Isso permite demonstrar o sistema sem precisar cadastrar tudo do zero durante a apresentacao.

### Organizacao em classes e metodos

O projeto esta organizado em pacotes e segue MVC:

```text
src/a3/nicolas/
├── model
├── model/enums
├── estruturas
├── controller
└── view
```

Cada classe tem uma responsabilidade clara, evitando misturar regra de negocio, estrutura de dados e interface grafica.

## 3. Arquitetura MVC

MVC separa o sistema em tres partes principais.

### Model

Pacote:

```text
src/a3/nicolas/model
```

Responsavel por representar os dados do sistema.

Classes:

- `Usuario`: representa a pessoa que abre ou possui um chamado.
- `Chamado`: representa o chamado tecnico, com descricao de abertura e descricao de fechamento.
- `Prioridade`: enum com `BAIXA`, `MEDIA` e `ALTA`.
- `StatusChamado`: enum com `ABERTO`, `EM_ATENDIMENTO` e `FECHADO`.

O Model nao conhece telas, botoes ou tabelas.

### Controller

Pacote:

```text
src/a3/nicolas/controller
```

Responsavel por controlar as regras do sistema e manipular as estruturas.

Classes:

- `UsuarioController`: cadastra, lista, busca e remove usuarios.
- `ChamadoController`: abre chamados, atende o proximo, fecha chamados, lista fila, lista historico e busca por ID.

A View chama o Controller, e o Controller devolve dados prontos para exibicao.

### View

Pacote:

```text
src/a3/nicolas/view
```

Responsavel pela interface grafica Swing.

Classes:

- `MainFrame`: janela principal com menu lateral e `CardLayout`.
- `PainelDashboard`: resumo do sistema.
- `PainelChamados`: abertura de chamados e tabela da fila.
- `PainelAtendimento`: atendimento e fechamento dos chamados.
- `PainelHistorico`: historico vindo da Pilha.
- `PainelUsuarios`: cadastro e listagem de usuarios.
- `PainelBusca`: busca por ID usando Arvore Binaria.

A View nao acessa diretamente as estruturas de dados.

## 4. Estruturas de dados

As estruturas foram implementadas manualmente, com nos internos, sem usar `ArrayList`, `LinkedList`, `HashMap` ou colecoes prontas dentro das estruturas.

### Fila

Arquivo:

```text
src/a3/nicolas/estruturas/Fila.java
```

A Fila segue a regra FIFO:

```text
First In, First Out
```

Ou seja, o primeiro chamado que entra e o primeiro chamado atendido.

Uso no sistema:

- Chamados abertos aguardam atendimento na Fila.
- Ao abrir chamado: `enqueue`.
- Ao atender proximo: `dequeue`.

Metodos:

- `enqueue(T valor)`.
- `dequeue()`.
- `peek()`.
- `isEmpty()`.
- `tamanho()`.
- `listar()`.

### Pilha

Arquivo:

```text
src/a3/nicolas/estruturas/Pilha.java
```

A Pilha segue a regra LIFO:

```text
Last In, First Out
```

Ou seja, o ultimo chamado fechado aparece primeiro no historico.

Uso no sistema:

- Chamados fechados sao enviados para a Pilha.
- Ao fechar chamado: `push`.
- O historico lista do topo para a base.

Metodos:

- `push(T valor)`.
- `pop()`.
- `peek()`.
- `isEmpty()`.
- `tamanho()`.
- `listar()`.

### Lista Ligada

Arquivo:

```text
src/a3/nicolas/estruturas/ListaLigada.java
```

A Lista Ligada armazena os usuarios cadastrados.

Uso no sistema:

- Adicionar usuarios.
- Remover usuario selecionado.
- Listar usuarios.
- Buscar usuario por ID pelo controller.

Metodos:

- `adicionar(T valor)`.
- `remover(T valor)`.
- `buscar(T valor)`.
- `listar()`.
- `tamanho()`.

### Arvore Binaria

Arquivo:

```text
src/a3/nicolas/estruturas/ArvoreBinaria.java
```

A Arvore Binaria organiza chamados pelo ID.

Regra:

- IDs menores ficam a esquerda.
- IDs maiores ficam a direita.

Uso no sistema:

- Todo chamado criado e inserido na arvore.
- A tela `Busca` procura chamados por ID.
- `listarEmOrdem()` mostra os chamados ordenados por ID.

Metodos:

- `inserir(Chamado chamado)`.
- `buscar(int id)`.
- `listarEmOrdem()`.
- `tamanho()`.

## 5. Funcionalidades por tela

### Dashboard

Mostra:

- Quantidade de chamados na fila.
- Quantidade de chamados no historico.
- Ultimo chamado fechado.

O ultimo chamado so aparece depois do fechamento, para nao tratar um chamado em atendimento como concluido.

### Usuarios

Permite:

- Cadastrar usuario.
- Listar usuarios.
- Remover usuario selecionado.

Os usuarios ficam armazenados na Lista Ligada.

### Chamados

Permite:

- Selecionar um usuario.
- Escolher prioridade.
- Informar descricao do problema.
- Abrir chamado tecnico.
- Visualizar a fila atual.

Ao abrir, o chamado entra na Fila e na Arvore Binaria.

### Atender

Permite:

- Atender o proximo chamado da Fila.
- Visualizar dados do chamado.
- Preencher descricao de fechamento.
- Fechar chamado.

A descricao de fechamento registra a solucao aplicada, como em sistemas de chamados profissionais.

### Historico

Mostra os chamados fechados.

A origem dos dados e a Pilha, por isso o chamado mais recente aparece primeiro.

Tambem exibe a descricao de fechamento.

### Busca

Permite buscar chamado por ID.

A busca usa a Arvore Binaria, e a tela tambem lista todos os chamados ordenados por ID.

## 6. Fluxo principal do sistema

Fluxo recomendado para demonstracao:

1. Abrir o sistema.
2. Mostrar os usuarios ja carregados.
3. Mostrar os chamados aguardando na fila.
4. Clicar em `Atender proximo`.
5. Conferir os dados do chamado.
6. Preencher a descricao de fechamento.
7. Clicar em `Fechar chamado`.
8. Mostrar o chamado no historico.
9. Buscar o chamado pelo ID.
10. Mostrar o Dashboard atualizado.

## 7. Como compilar e executar

Compilar:

```powershell
javac -Xlint:serial -d out src\a3\nicolas\*.java src\a3\nicolas\model\*.java src\a3\nicolas\model\enums\*.java src\a3\nicolas\estruturas\*.java src\a3\nicolas\controller\*.java src\a3\nicolas\view\*.java src\a3\nicolas\view\panels\*.java
```

Executar:

```powershell
java -cp out a3.nicolas.Main
```

## 8. Pontos importantes para explicar na apresentacao

### Por que MVC?

MVC ajuda a separar responsabilidades:

- Model guarda dados.
- Controller aplica regras.
- View mostra a interface e captura eventos.

Isso deixa o projeto mais organizado e facilita manutencao.

### Onde cada estrutura aparece?

- Fila: chamados abertos aguardando atendimento.
- Pilha: historico de chamados fechados.
- Lista Ligada: usuarios cadastrados.
- Arvore Binaria: busca e ordenacao dos chamados por ID.

### Por que nao ha banco de dados?

O objetivo do projeto era usar estruturas de dados em memoria.

Por isso, ao fechar o programa, os dados cadastrados durante aquela execucao sao perdidos.

### Por que existem dados iniciais?

Eles ajudam na demonstracao, evitando gastar tempo cadastrando tudo ao vivo.

### Por que a descricao de fechamento foi adicionada?

Ela deixa o fechamento mais parecido com sistemas reais de suporte, como Jira, GLPI ou ServiceNow.

O tecnico nao apenas fecha o chamado: ele registra a solucao aplicada ou a observacao final.

## 9. Melhorias e correcoes feitas

Durante a revisao do projeto, foram feitos ajustes para deixar a demonstracao mais segura:

- O menu lateral foi corrigido para ter exatamente a quantidade de linhas necessaria.
- O Dashboard passou a mostrar o ultimo chamado somente depois do fechamento.
- O sistema impede atender outro chamado antes de fechar o atual.
- O fechamento exige uma descricao de solucao.
- As telas Swing receberam `serialVersionUID`.
- Campos nao serializaveis da View foram marcados como `transient`.
- A compilacao com `-Xlint:serial` passou sem warnings.

## 10. Conclusao

O sistema atende aos requisitos propostos:

- Foi desenvolvido em Java.
- Esta relacionado ao suporte tecnico de informatica.
- Usa Fila, Pilha, Lista Ligada e Arvore Binaria.
- Permite cadastro de usuarios.
- Permite criacao de chamados tecnicos.
- Permite atendimento, fechamento e historico de chamados.
- Permite consulta, listagem e busca de informacoes.
- Possui dados iniciais para demonstracao.
- Esta organizado em classes, metodos e pacotes.

O projeto demonstra, de forma pratica, como estruturas de dados podem ser aplicadas em um sistema com interface grafica e organizacao MVC.
