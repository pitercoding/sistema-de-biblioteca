package biblioteca.model;

public class Livro {
    // Atributos privados (encapsulamento)
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private boolean disponivel;

    // Construtor
    public Livro(String titulo, String autor, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.disponivel = true;
    }

    // Métodos Getters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    // Método Getter especial para boolean
    public boolean isDisponivel() {
        return disponivel;
    }

    // Métodos de Negócio (ao invés de setDisponivel)
    public void marcarComoEmprestado() {
        this.disponivel = false;
    }

    public void marcarComoDisponivel (){
        this.disponivel = true;
    }

    // Representação textual dos livros
    @Override
    public String toString() {
        return "Livro {" +
                "Título='" + titulo + '\'' +
                ", Autor='" + autor + '\'' +
                ", Ano=" + anoPublicacao +
                ", Disponível=" + (disponivel ? "Sim" : "Não") +
                '}';
    }
}
