package biblioteca.ui;

import biblioteca.model.Livro;
import biblioteca.model.Usuario;
import biblioteca.service.BibliotecaService;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
            System.out.println("7. Listar Livros Emprestados de um Usuário");
            System.out.println("8. Listar Usuários");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = -1;

            // Loop para validar entrada numérica
            while (true) {
                System.out.print("Escolha uma opção (0 a 8): ");
                String entrada = scanner.nextLine().trim();

                try {
                    opcao = Integer.parseInt(entrada);

                    if (opcao < 0 || opcao > 8) {
                        System.out.println("⚠️ Por favor, digite um número inteiro entre 0 e 8.");
                    } else {
                        break; // entrada válida
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Entrada inválida! Digite apenas números inteiros de 0 a 8.");
                }
            }


            try {
                switch (opcao) {
                    case 1 -> cadastrarLivro();
                    case 2 -> cadastrarUsuario();
                    case 3 -> listarLivrosDisponiveis();
                    case 4 -> emprestarLivro();
                    case 5 -> devolverLivro();
                    case 6 -> deletarLivro();
                    case 7 -> listarEmprestimosPorUsuario();
                    case 8 -> listarUsuarios();
                    case 0 -> {
                        executando = false;
                        System.out.println("\nEncerrando sistema...");
                    }
                    default -> System.out.println("\nOpção inválida! Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("\n[Erro!] " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void cadastrarLivro() {
        System.out.println("\n==================");
        System.out.println("CADASTRO DE LIVRO");
        System.out.println("==================");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Ano de publicação: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        Livro livro = new Livro(titulo, autor, ano);
        biblioteca.cadastrarLivro(livro);
        System.out.println("\n✅ Livro cadastrado com sucesso!");
    }

    private void cadastrarUsuario() {
        System.out.println("\n====================");
        System.out.println("CADASTRO DE USUÁRIO");
        System.out.println("====================");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        Usuario usuario = new Usuario(nome, matricula);
        biblioteca.cadastrarUsuario(usuario);
        System.out.println("\n✅ Usuário cadastrado com sucesso!");
    }

    private void listarLivrosDisponiveis() {
        List<Livro> disponiveis = biblioteca.listarLivrosDisponiveis();

        System.out.println("\n===================");
        System.out.println("LIVROS DISPONÍVEIS");
        System.out.println("===================");

        if (disponiveis.isEmpty()) {
            System.out.println("Nenhum livro disponível no momento.");
            return;
        }

        int contador = 1;
        for (Livro livro : disponiveis) {
            System.out.printf("\n#%d\n%s\n", contador++, livro.toString());
        }

        System.out.printf("\nTotal de livros disponíveis: %d\n", disponiveis.size());
    }

    private void emprestarLivro() {
        System.out.println("\n================");
        System.out.println("EMPRESTAR LIVRO");
        System.out.println("================");
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
        System.out.println("\n================");
        System.out.println("DEVOLVER LIVRO");
        System.out.println("================");
        System.out.print("Matrícula do usuário: ");
        String matricula = scanner.nextLine();

        System.out.print("Título do livro: ");
        String tituloLivro = scanner.nextLine();

        List<Livro> livrosEmprestados = biblioteca.listarLivrosEmprestadosPorUsuario(matricula);
        Livro livroParaDevolver = livrosEmprestados.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(tituloLivro))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "[Erro!] Este usuário não possui o livro informado."
                ));

        biblioteca.devolverLivro(matricula, livroParaDevolver);
    }

    private void deletarLivro() {
        System.out.println("\n==============");
        System.out.println("DELETAR LIVRO");
        System.out.println("==============");

        System.out.print("Título do livro a deletar: ");
        String titulo = scanner.nextLine().trim(); // remove espaços extras

        if (titulo.isEmpty()) {
            System.out.println("⚠️ Título inválido! Operação cancelada.");
            return;
        }

        Set<String> respostasSim = Set.of("s", "sim");
        Set<String> respostasNao = Set.of("n", "não", "nao");

        String resposta;
        while (true) {
            System.out.print("Tem certeza que deseja deletar esse livro? [S/N]: ");
            resposta = scanner.nextLine().trim().toLowerCase();

            if (respostasNao.contains(resposta)) {
                System.out.println("❌ O livro não foi deletado!");
                return;
            } else if (respostasSim.contains(resposta)) {
                try {
                    biblioteca.removerLivro(titulo);
                    System.out.println("✅ Livro deletado com sucesso!");
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ Não foi possível deletar: " + e.getMessage());
                } catch (IllegalStateException e) {
                    System.out.println("❌ Não é possível deletar: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("❌ Ocorreu um erro inesperado: " + e.getMessage());
                }
                return;
            } else {
                System.out.println("⚠️ Informe uma resposta válida (S/N)!");
            }
        }
    }

    private void listarEmprestimosPorUsuario() {
        System.out.println("\n=======================");
        System.out.println("EMPRÉSTIMOS DO USUÁRIO");
        System.out.println("=======================");
        System.out.print("Matrícula do usuário: ");
        String matricula = scanner.nextLine();

        List<Livro> emprestados = biblioteca.listarLivrosEmprestadosPorUsuario(matricula);
        if (emprestados.isEmpty()) {
            System.out.println("Esse usuário não possui livros emprestados.");
            return;
        }

        int contador = 1;
        for (Livro livro : emprestados) {
            System.out.printf("\n#%d\n%s\n", contador++, livro.toString());
        }
        System.out.printf("\nTotal de livros emprestados: %d\n", emprestados.size());
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = biblioteca.listarUsuarios();

        System.out.println("\n===================");
        System.out.println("LISTA DE USUÁRIOS");
        System.out.println("===================");

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        int contador = 1;
        for (Usuario usuario : usuarios) {
            System.out.printf("#%d\n%s\n", contador++, usuario.toString());
            // opcional: listar títulos dos livros emprestados
            if (!usuario.getLivrosEmprestados().isEmpty()) {
                System.out.println("  Livros emprestados:");
                for (Livro livro : usuario.getLivrosEmprestados()) {
                    System.out.printf("    - %s\n", livro.getTitulo());
                }
            }
        }
        System.out.printf("Total de usuários: %d\n", usuarios.size());
    }
}
