package biblioteca.dao;

import biblioteca.model.Livro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    public void registrarEmprestimo(int usuarioId, int livroId) {
        String sql = "INSERT INTO emprestimos (usuario_id, livro_id) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setInt(2, livroId);
            stmt.executeUpdate();

            System.out.println("✅ Empréstimo registrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao registrar empréstimo: " + e.getMessage());
        }
    }

    public void registrarDevolucao(int usuarioId, int livroId) {
        String sql = "UPDATE emprestimos SET data_devolucao = NOW() " +
                "WHERE usuario_id = ? AND livro_id = ? AND data_devolucao IS NULL";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setInt(2, livroId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Devolução registrada com sucesso!");
            } else {
                System.out.println("⚠️ Nenhum empréstimo ativo encontrado para esse livro/usuário.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao registrar devolução: " + e.getMessage());
        }
    }

    public List<Livro> listarEmprestimosAtivosPorUsuario(int usuarioId) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT l.id, l.titulo, l.autor, l.ano_publicacao, l.disponivel " +
                "FROM emprestimos e " +
                "JOIN livros l ON e.livro_id = l.id " +
                "WHERE e.usuario_id = ? AND e.data_devolucao IS NULL";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Livro livro = new Livro(
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("ano_publicacao")
                    );
                    livro.setId(rs.getInt("id"));
                    livro.setDisponivel(rs.getBoolean("disponivel"));
                    livros.add(livro);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar empréstimos: " + e.getMessage());
        }

        return livros;
    }
}
