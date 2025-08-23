package biblioteca.service;

import biblioteca.dao.LivroDAO;
import biblioteca.dao.UsuarioDAO;
import biblioteca.model.Livro;
import biblioteca.model.Usuario;

import java.util.List;

public class BibliotecaService {
    private final LivroDAO livroDAO;
    private final UsuarioDAO usuarioDAO;

    public BibliotecaService() {
        this.livroDAO = new LivroDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public void cadastrarLivro(Livro livro) { livroDAO.salvar(livro); }

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

    public void cadastrarUsuario(Usuario usuario) { usuarioDAO.salvar(usuario); }

    public void emprestarLivro(String matricula, Livro livro) {
        Usuario usuario = usuarioDAO.listarTodos().stream()
                .filter(u -> u.getMatricula().equalsIgnoreCase(matricula))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        if (!livro.isDisponivel()) throw new IllegalStateException("Livro indisponível.");
        if (!usuario.podeEmprestar()) throw new IllegalStateException("Usuário atingiu limite de 3 livros.");

        livro.marcarComoEmprestado();
        usuario.adicionarLivro(livro);
        livroDAO.atualizarDisponibilidade(livro.getId(), false);
    }

    public void devolverLivro(String matricula, Livro livro) {
        Usuario usuario = usuarioDAO.listarTodos().stream()
                .filter(u -> u.getMatricula().equalsIgnoreCase(matricula))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        usuario.devolverLivro(livro);
        livro.marcarComoDisponivel();
        livroDAO.atualizarDisponibilidade(livro.getId(), true);
    }
}
