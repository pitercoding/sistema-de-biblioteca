# 📚 Sistema de Biblioteca (Java + MySQL)

Este é um projeto de aprendizado em Java que implementa um sistema de **biblioteca com persistência em banco de dados MySQL**.  
Ele foi desenvolvido para praticar os conceitos de **Programação Orientada a Objetos (POO)**, separação de responsabilidades e integração com JDBC.

O sistema roda via **console (Scanner)** e permite cadastrar livros e usuários, realizar empréstimos e devoluções, além de consultar livros disponíveis e emprestados.

## 🚀 Objetivos do Projeto

- Aplicar conceitos de **POO** (encapsulamento, classes de domínio, serviços e interface).
- Treinar **separação de responsabilidades** em pacotes:
  - `model` → classes de domínio (`Livro`, `Usuario`).
  - `dao` → camada de persistência com JDBC (`ConnectionFactory`, `LivroDAO`, `UsuarioDAO`, `EmprestimoDAO`).
  - `service` → regras de negócio (`BibliotecaService`).
  - `ui` → interface via console (`App´`, `ConsoleUI`).
- Implementar **regras de negócio reais**, como:
  - ➡️ Limite de 3 livros emprestados por usuário.
  - ➡️ Controle de disponibilidade de livros.
  - ➡️ Validação ao devolver apenas livros realmente emprestados.
- Criar um **CRUD completo** para livros e usuários.

## 🏗️ Estrutura do Projeto
```markdown
biblioteca/
├── dao/ # Camada de acesso ao banco de dados (ConnectionFactory, EmprestimoDAO, LivroDAO, UsuarioDAO)
├── model/ # Classes de domínio (Livro, Usuario)
├── service/ # Regras de negócio (BibliotecaService)
└── ui/ # Interface de console (App, ConsoleUI)
```
## 🖥️ Funcionalidades Implementadas

- **Cadastro de Livros**
  - Persistência no banco de dados MySQL.
  - Controle de disponibilidade (`disponivel`).
  - Possibilidade de cadastrar e deletar livros.

- **Cadastro de Usuários**
  - Persistência no banco de dados.
  - Cada usuário é identificado pela **matrícula**.
  - Validações ao cadastrar para evitar duplicações.

- **Empréstimo de Livros**
  - Apenas livros **disponíveis** podem ser emprestados.
  - Atualiza a disponibilidade no banco de dados.
  - Usuário não pode ultrapassar o limite de **3 livros**.

- **Devolução de Livros**
  - Valida se o usuário realmente possui o livro no banco antes de devolver.
  - Atualiza a disponibilidade do livro e remove o vínculo no banco.

- **Listagem de Livros Disponíveis**
  - Exibe todos os livros que podem ser emprestados.

- **Listagem de Livros Emprestados por Usuário**
  - Mostra exatamente quais livros cada usuário possui em aberto no banco.

## ▶️ Exemplo de Uso (Console)
Ao executar o programa, o menu exibido será:

```markdown
===== Sistema de Biblioteca =====
1. Cadastrar Livro
2. Cadastrar Usuário
3. Listar Livros Disponíveis
4. Emprestar Livro
5. Devolver Livro
6. Deletar Livro
7. Listar Livros Emprestados de um Usuário
0. Sair
Escolha uma opção:
```
### 🔹 Exemplo 1 → Cadastrar um livro
```java
Escolha uma opção: 1
Título: O Senhor dos Anéis
Autor: J.R.R. Tolkien
Ano de publicação: 1954
Livro cadastrado com sucesso!
```
### 🔹 Exemplo 2 → Cadastrar um usuário
```java
Escolha uma opção: 2
Nome: João Silva
Matrícula: 123
Usuário cadastrado com sucesso!
```
### 🔹 Exemplo 3 → Emprestar um livro
```java
Escolha uma opção: 4
Matrícula do usuário: 123
Título do livro: O Senhor dos Anéis
Livro emprestado com sucesso!
```
### 🔹 Exemplo 4 → Devolver um livro
```java
Escolha uma opção: 5
Matrícula do usuário: 123
Título do livro: O Senhor dos Anéis
Livro devolvido com sucesso!
```

## ⚙️ Ajustes Recentes

- Refatoração do **`BibliotecaService`**:
  - Toda validação de empréstimo e devolução agora é feita **diretamente no banco**, evitando inconsistências com listas em memória.
  - Correção da regra de devolução (antes não encontrava o livro emprestado corretamente).
  - Implementação robusta do limite de **3 livros por usuário**.

- Refatoração da **`ConsoleUI`**:
  - Ajustada para consultar livros diretamente no banco.
  - Correção da opção **5 (Devolver Livro)**, que agora funciona corretamente.

## 🛠️ Banco de Dados (MySQL)

O sistema utiliza **MySQL** como banco de dados.  
Aqui está o script SQL para criar as tabelas necessárias:

```sql
CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    matricula VARCHAR(50) UNIQUE NOT NULL
);

-- Tabela de livros
CREATE TABLE IF NOT EXISTS livros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    ano_publicacao INT NOT NULL,
    disponivel BOOLEAN DEFAULT TRUE
);

-- Tabela de empréstimos
CREATE TABLE IF NOT EXISTS emprestimos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    livro_id INT NOT NULL,
    data_emprestimo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_devolucao TIMESTAMP NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (livro_id) REFERENCES livros(id)
);
```

## 📌 Configuração JDBC
No código, configure a conexão com MySQL (`ConnectionFactory` ou equivalente) com os dados do seu ambiente:
```java
private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
private static final String USER = "root";
private static final String PASSWORD = "sua_senha";
```

## 📅 Melhorias Futuras
  
- IDs automáticos para livros e usuários (já implementado via MySQL).  
- Interface gráfica (JavaFX ou Swing).  
- Melhorar a busca de livros e usuários (uso de `Map` em cache). 
- Relatórios mais detalhados de empréstimos. 
- Testes unitários com JUnit.  
- Scripts adicionais para popular o banco com dados de exemplo.

## 🤝 Contribuições

Este projeto está aberto para contribuições!  
Se você também está aprendendo Java ou quer ajudar com ideias e melhorias, sinta-se à vontade para abrir um Pull Request ou Issue.

## 👨‍💻 Autor

Projeto desenvolvido por um estudante de Java com fins educacionais e de aprendizado pessoal.  
Se quiser trocar ideia ou colaborar, é só chamar!

## 📄 Licença

Este projeto está sob a licença MIT. Sinta-se livre para usar, modificar e compartilhar.

## 🏷️ Palavras-chave

<b>biblioteca, sistema-biblioteca, java, pojo, dao, jdbc, mysql, crud, consoleui, emprestimo-livros, persistencia-dados

