import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DistribuitorService {
    private Connection conn;

    public DistribuitorService() throws SQLException {
        this.conn = DbConnection.getInstance();
    }

    public void insertDistribuitor(Distribuitor d) throws SQLException {
        String sql = "INSERT INTO distribuitor (nume, locatie) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, d.getNume());
            stmt.setString(2, d.getLocatie());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                d.setId(rs.getInt(1));
            }
        }
    }

    public List<Distribuitor> getDistribuitori() throws SQLException {
        List<Distribuitor> lista = new ArrayList<>();
        String sql = "SELECT * FROM distribuitor";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                String locatie = rs.getString("locatie");
                lista.add(new Distribuitor(id, nume, locatie));
            }
        }
        return lista;
    }

    public Distribuitor getDistribuitorById(int id) throws SQLException {
        String sql = "SELECT * FROM distribuitor WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Distribuitor(
                            rs.getInt("id"),
                            rs.getString("nume"),
                            rs.getString("locatie")
                    );
                }
            }
        }
        return null;
    }

    public void updateDistribuitor(Distribuitor d, int id) throws SQLException {
        String sql = "UPDATE distribuitor SET nume = ?, locatie = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getNume());
            stmt.setString(2, d.getLocatie());
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
    }

    public void deleteDistribuitor(int id) throws SQLException {
        String sql = "DELETE FROM distribuitor WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
