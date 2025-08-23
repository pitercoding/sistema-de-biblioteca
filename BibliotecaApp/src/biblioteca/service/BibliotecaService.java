package biblioteca.service;

import biblioteca.dao.LivroDAO;
import biblioteca.model.Livro;
import java.util.List;

public class BibliotecaService {

    private final LivroDAO livroDAO = new LivroDAO();

    public void registrarLivro(Livro livro) {
        livroDAO.salvar(livro);
    }

    public List<Livro> listarLivrosDisponiveis() {
        return livroDAO.listarTodos()
                .stream()
                .filter(Livro::isDisponivel)
                .toList();
    }
}

