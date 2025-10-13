package biblioteca.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties props = loadProperties();

            String url = props.getProperty("bd.URL");
            String user = props.getProperty("bd.USER");
            String password = props.getProperty("bd.PASSWORD");

            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC não encontrado!", e);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o arquivo de propriedades da conexão!", e);
        }
    }

    private static Properties loadProperties() throws IOException {
        Properties props = new Properties();
        String fileName = "/biblioteca/connection.properties";
        try(InputStream input = ConnectionFactory.class.getResourceAsStream(fileName)) {
            if (input == null) {
                throw new IOException("Arquivo '" + fileName + "' não encontrado no classpath!");
            }
            props.load(input);
        }
        return props;
    }
}
