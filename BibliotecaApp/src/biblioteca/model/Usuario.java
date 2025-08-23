package biblioteca.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int id;
    private String nome;
    private String matricula;
    private List<Livro> livrosEmprestados;

    public Usuario(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
        this.livrosEmprestados = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }

    public List<Livro> getLivrosEmprestados() { return new ArrayList<>(livrosEmprestados); }

    public boolean podeEmprestar() { return livrosEmprestados.size() < 3; }

    public void adicionarLivro(Livro livro) {
        if (podeEmprestar()) livrosEmprestados.add(livro);
        else throw new IllegalStateException("Usuário já atingiu o limite de 3 livros.");
    }

    public void devolverLivro(Livro livro) {
        if (livrosEmprestados.contains(livro)) livrosEmprestados.remove(livro);
        else throw new IllegalArgumentException("Este usuário não possui o livro informado.");
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", Nome='" + nome + '\'' +
                ", Matrícula='" + matricula + '\'' +
                ", Livros emprestados=" + livrosEmprestados.size() +
                '}';
    }
}
