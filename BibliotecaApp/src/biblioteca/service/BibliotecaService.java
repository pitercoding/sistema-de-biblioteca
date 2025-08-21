package biblioteca.service;

import biblioteca.model.Livro;
import biblioteca.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaService {
    // Atributos
    private List<Livro> livros;
    private List<Usuario> usuarios;

    // Construtor
    public BibliotecaService() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    // --- 📚 Operações com Livros ---
    public void cadastrarLivro(Livro livro) {
        livros.add(livro);
        System.out.println("Livro cadastrado: " + livro);
    }

    public List<Livro> listarLivrosDisponiveis() {
        List<Livro> disponiveis = new ArrayList<>();
        for (Livro livro : livros) {
            if (livro.isDisponivel()) {
                disponiveis.add(livro);
            }
        }
        return disponiveis;
    }

    // --- 👤 Operações com Usuários ---
    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuário cadastrado: " + usuario);
    }

    public Usuario buscarUsuarioPorMatricula(String matricula) {
        for (Usuario usuario : usuarios) {
            if (usuario.getMatricula().equalsIgnoreCase(matricula)) {
                return usuario;
            }
        }
        throw new IllegalArgumentException("Usuário com matrícula " + matricula + " não encontrado.");
    }

    // --- 📖 Empréstimo e Devolução ---
    public void emprestarLivro(String matricula, Livro livro) {
        Usuario usuario = buscarUsuarioPorMatricula(matricula);

        if (!livro.isDisponivel()) {
            throw new IllegalStateException("O livro '" + livro.getTitulo() + "' não está disponível.");
        }

        if (!usuario.podeEmprestar()) {
            throw new IllegalStateException("Usuário já atingiu o limite de empréstimos.");
        }

        usuario.adicionarLivro(livro);
        livro.marcarComoEmprestado();
        System.out.println("Livro emprestado: " + livro.getTitulo() + " para usuário " + usuario.getNome());
    }

    public void devolverLivro(String matricula, Livro livro) {
        Usuario usuario = buscarUsuarioPorMatricula(matricula);

        usuario.devolverLivro(livro);
        livro.marcarComoDisponivel();
        System.out.println("Livro devolvido: " + livro.getTitulo() + " pelo usuário " + usuario.getNome());
    }
}
