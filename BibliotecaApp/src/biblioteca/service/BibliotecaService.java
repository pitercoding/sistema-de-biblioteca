package biblioteca.service;

import biblioteca.dao.LivroDAO;
import biblioteca.dao.UsuarioDAO;
import biblioteca.dao.EmprestimoDAO;
import biblioteca.model.Livro;
import biblioteca.model.Usuario;

import java.util.List;

public class BibliotecaService {
    private final LivroDAO livroDAO;
    private final UsuarioDAO usuarioDAO;
    private final EmprestimoDAO emprestimoDAO;

    public BibliotecaService() {
        this.livroDAO = new LivroDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.emprestimoDAO = new EmprestimoDAO();
    }

    // === Livros ===
    public void cadastrarLivro(Livro livro) {
        livroDAO.salvar(livro);
    }

    public void removerLivro(String titulo) {
        Livro livro = livroDAO.listarTodos().stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado."));
        livroDAO.deletarLivro(livro.getId());
    }

    public List<Livro> listarLivrosDisponiveis() {
        return livroDAO.listarTodos().stream().filter(Livro::isDisponivel).toList();
    }

    // === Usuários ===
    public void cadastrarUsuario(Usuario usuario) {
        usuarioDAO.salvar(usuario);
    }

    // === Empréstimos ===
    public void emprestarLivro(String matricula, Livro livro) {
        Usuario usuario = usuarioDAO.listarTodos().stream()
                .filter(u -> u.getMatricula().equalsIgnoreCase(matricula))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        // Busca a quantidade de livros realmente emprestados no banco
        int livrosAtivos = emprestimoDAO.listarEmprestimosAtivosPorUsuario(usuario.getId()).size();

        if (!livro.isDisponivel()) throw new IllegalStateException("Livro indisponível.");
        if (livrosAtivos >= 3) throw new IllegalStateException("Usuário atingiu limite de 3 livros.");

        // Atualiza no banco
        livroDAO.atualizarDisponibilidade(livro.getId(), false);
        emprestimoDAO.registrarEmprestimo(usuario.getId(), livro.getId());
    }

    public void devolverLivro(String matricula, Livro livro) {
        Usuario usuario = usuarioDAO.listarTodos().stream()
                .filter(u -> u.getMatricula().equalsIgnoreCase(matricula))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        // Verifica se o usuário realmente possui esse livro emprestado no banco
        List<Livro> livrosEmprestados = emprestimoDAO.listarEmprestimosAtivosPorUsuario(usuario.getId());
        boolean possuiLivro = livrosEmprestados.stream()
                .anyMatch(l -> l.getId() == livro.getId());

        if (!possuiLivro) throw new IllegalArgumentException("Este usuário não possui o livro informado.");

        // Atualiza no banco
        livroDAO.atualizarDisponibilidade(livro.getId(), true);
        emprestimoDAO.registrarDevolucao(usuario.getId(), livro.getId());
    }

    public List<Livro> listarLivrosEmprestadosPorUsuario(String matricula) {
        Usuario usuario = usuarioDAO.listarTodos().stream()
                .filter(u -> u.getMatricula().equalsIgnoreCase(matricula))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        return emprestimoDAO.listarEmprestimosAtivosPorUsuario(usuario.getId());
    }
}



