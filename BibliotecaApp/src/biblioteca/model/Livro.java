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

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    // Métodos de negócio
    public void marcarComoEmprestado() { this.disponivel = false; }
    public void marcarComoDisponivel() { this.disponivel = true; }

    // Sobrescrevendo equals e hashCode para comparar livros pelo ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Livro)) return false;
        Livro livro = (Livro) o;
        return id == livro.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

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
