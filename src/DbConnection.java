import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/my_db?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "roby";

    private static Connection connection;

    private DbConnection() {}

    public static Connection getInstance() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASS);
        }
        return connection;
    }
}