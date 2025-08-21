package biblioteca.ui;

import biblioteca.model.Livro;
import biblioteca.model.Usuario;
import biblioteca.service.BibliotecaService;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final BibliotecaService biblioteca;
    private final Scanner scanner;

    public ConsoleUI() {
        this.biblioteca = new BibliotecaService();
        this.scanner = new Scanner(System.in);
    }

    public void iniciarSistema() {
        boolean executando = true;

        while (executando) {
            System.out.println("\n===== Sistema de Biblioteca =====");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Cadastrar Usuário");
            System.out.println("3. Listar Livros Disponíveis");
            System.out.println("4. Emprestar Livro");
            System.out.println("5. Devolver Livro");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // consumir quebra de linha

            try {
                switch (opcao) {
                    case 1 -> cadastrarLivro();
                    case 2 -> cadastrarUsuario();
                    case 3 -> listarLivrosDisponiveis();
                    case 4 -> emprestarLivro();
                    case 5 -> devolverLivro();
                    case 0 -> {
                        executando = false;
                        System.out.println("Encerrando sistema...");
                    }
                    default -> System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private void cadastrarLivro() {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Ano de publicação: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        Livro livro = new Livro(titulo, autor, ano);
        biblioteca.cadastrarLivro(livro);
    }

    private void cadastrarUsuario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        Usuario usuario = new Usuario(nome, matricula);
        biblioteca.cadastrarUsuario(usuario);
    }

    private void listarLivrosDisponiveis() {
        List<Livro> disponiveis = biblioteca.listarLivrosDisponiveis();
        if (disponiveis.isEmpty()) {
            System.out.println("Nenhum livro disponível.");
        } else {
            System.out.println("Livros disponíveis:");
            disponiveis.forEach(System.out::println);
        }
    }

    private void emprestarLivro() {
        System.out.print("Matrícula do usuário: ");
        String matricula = scanner.nextLine();

        System.out.print("Título do livro: ");
        String tituloLivro = scanner.nextLine();

        Livro livro = biblioteca.listarLivrosDisponiveis().stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(tituloLivro))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado."));

        biblioteca.emprestarLivro(matricula, livro);
    }

    private void devolverLivro() {
        System.out.print("Matrícula do usuário: ");
        String matricula = scanner.nextLine();

        System.out.print("Título do livro: ");
        String tituloLivro = scanner.nextLine();

        // Aqui idealmente buscaríamos o livro na lista do usuário
        Livro livro = biblioteca.listarLivrosDisponiveis().stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(tituloLivro))
                .findFirst()
                .orElse(null);

        if (livro == null) {
            System.out.println("Livro já está disponível ou título incorreto.");
        } else {
            biblioteca.devolverLivro(matricula, livro);
        }
    }
}
