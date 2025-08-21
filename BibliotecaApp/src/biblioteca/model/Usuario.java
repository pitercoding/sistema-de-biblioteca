package biblioteca.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    // Atributos privados (encapsulamento)
    private String nome;
    private String matricula;
    private List<Livro> livrosEmprestados;

    // Construtor
    public Usuario(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
        this.livrosEmprestados = new ArrayList<>();
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public List<Livro> getLivrosEmprestados() {
        return new ArrayList<>(livrosEmprestados);
        // retorna cópia para não permitir alterações diretas na lista
    }

    // Métodos de negócio
    public boolean podeEmprestar() {
        return livrosEmprestados.size() < 3; // regra: no máx. 3 livros
    }

    public void adicionarLivro(Livro livro) {
        if (podeEmprestar()) {
            livrosEmprestados.add(livro);
        } else {
            throw new IllegalStateException("Usuário já atingiu o limite de 3 empréstimos.");
        }
    }

    public void devolverLivro(Livro livro) {
        if (livrosEmprestados.contains(livro)) {
            livrosEmprestados.remove(livro);
        } else {
            throw new IllegalArgumentException("Este usuário não possui o livro informado.");
        }
    }

    // Representação textual
    @Override
    public String toString() {
        return "Usuário {" +
                "Nome='" + nome + '\'' +
                ", Matrícula='" + matricula + '\'' +
                ", Livros emprestados=" + livrosEmprestados.size() +
                '}';
    }
}
