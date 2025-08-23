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
            System.out.println("6. Deletar Livro");
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
                    case 6 -> deletarLivro();
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
        System.out.println("Livro cadastrado com sucesso!");
    }

    private void cadastrarUsuario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        Usuario usuario = new Usuario(nome, matricula);
        biblioteca.cadastrarUsuario(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
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
        System.out.println("Livro emprestado com sucesso!");
    }

    private void devolverLivro() {
        System.out.print("Matrícula do usuário: ");
        String matricula = scanner.nextLine();

        System.out.print("Título do livro: ");
        String tituloLivro = scanner.nextLine();

        Livro livro = biblioteca.listarLivrosDisponiveis().stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(tituloLivro))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado ou já disponível."));

        biblioteca.devolverLivro(matricula, livro);
        System.out.println("Livro devolvido com sucesso!");
    }

    private void deletarLivro() {
        System.out.print("Título do livro a deletar: ");
        String titulo = scanner.nextLine();
        biblioteca.removerLivro(titulo);
        System.out.println("Livro deletado com sucesso!");
    }
}
