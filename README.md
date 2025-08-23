# 📚 Sistema de Biblioteca (Java)

Este é um projeto de aprendizado em Java que implementa um sistema simples de biblioteca para praticar os conceitos de Programação Orientada a Objetos (POO).

O sistema permite cadastrar livros e usuários, realizar empréstimos e devoluções, além de consultar livros disponíveis e emprestados.  
Atualmente, ele roda via console (Scanner) e **persiste os dados em um banco de dados MySQL**, mas será evoluído futuramente para versões mais robustas.

## 🚀 Objetivos do Projeto

- Aplicar conceitos básicos de POO (encapsulamento, classes de domínio, serviços e interface).
- Treinar separação de responsabilidades em pacotes (model, service, ui, dao).
- Simular regras de negócio simples, como:
  <br>➡️ Limite de 3 livros por usuário.
  <br>➡️ Controle de disponibilidade de livros.
  <br>➡️ Validações ao devolver ou emprestar.
- Praticar **persistência de dados com JDBC e MySQL**.
- Aprender CRUD completo para livros e usuários (cadastrar, listar, atualizar disponibilidade, deletar).

## 🏗️ Estrutura do Projeto

<p align="center"><img width="237" height="388" alt="image" src="https://github.com/user-attachments/assets/dfb69519-410f-432e-81c5-865eee991c7c" /></p>


## 🖥️ Funcionalidades Implementadas

- **Cadastro de Livros**  
  - Persistência no banco de dados MySQL.
  - Controle de disponibilidade (`disponivel`).
  - Possibilidade de adicionar ou deletar livros.

- **Cadastro de Usuários**  
  - Persistência no banco de dados.
  - Cada usuário possui lista de livros emprestados.
  - Limite de **3 livros emprestados** por usuário.

- **Empréstimo de Livros**  
  - Só livros disponíveis podem ser emprestados.
  - Atualiza a disponibilidade do livro no banco.

- **Devolução de Livros**  
  - Atualiza a disponibilidade no banco.
  - Valida se o usuário realmente possui o livro.

- **Listagem de Livros Disponíveis**  
  - Mostra todos os livros que estão disponíveis para empréstimo.

<p align="center"><img width="395" height="277" alt="image" src="https://github.com/user-attachments/assets/745ae9ce-06f9-4cd7-83fe-88db424d1f4d" /></p>

## 📅 Melhorias Futuras

Este projeto está em fase inicial de aprendizado e será melhorado no futuro com:  
- IDs automáticos para livros e usuários.  
- Interface gráfica (JavaFX ou Swing).  
- Busca mais rápida com uso de Map.  
- Armazenamento de dados robusto no banco (atualmente já há persistência básica).  
- Testes unitários com JUnit.  
- Relatórios de livros emprestados por usuário.

## 🤝 Contribuições

Este projeto está aberto para contribuições!  
Se você também está aprendendo Java ou quer ajudar com ideias e melhorias, sinta-se à vontade para abrir um Pull Request ou Issue.

## 👨‍💻 Autor

Projeto desenvolvido por um estudante de Java com fins educacionais e de aprendizado pessoal.  
Se quiser trocar ideia ou colaborar, é só chamar!

## 📄 Licença

Este projeto está sob a licença MIT. Sinta-se livre para usar, modificar e compartilhar.

## 🏷️ Palavras-chave

biblioteca, sistema-biblioteca, java, pojo, dao, jdbc, mysql, crud, consoleui, emprestimo-livros, persistencia-dados

