package biblioteca.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // força o carregamento do driver JDBC
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC não encontrado!", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


