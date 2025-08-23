package biblioteca.model;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private boolean disponivel;

    public Livro(String titulo, String autor, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.disponivel = true;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public boolean isDisponivel() { return disponivel; }

    public void marcarComoEmprestado() { this.disponivel = false; }
    public void marcarComoDisponivel() { this.disponivel = true; }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", Título='" + titulo + '\'' +
                ", Autor='" + autor + '\'' +
                ", Ano=" + anoPublicacao +
                ", Disponível=" + (disponivel ? "Sim" : "Não") +
                '}';
    }
}
