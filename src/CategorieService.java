import java.sql.*;
import java.util.*;

public class CategorieService {
    private final Connection conn;

    public CategorieService() throws SQLException {
        this.conn = DbConnection.getInstance();
    }

    public void insertCategorie(Categorie categorie) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Categorie(nume) VALUES (?)")) {
            stmt.setString(1, categorie.getNume());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Categorie> getAll() {
        List<Categorie> list = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Categorie");
            while (rs.next()) {
                list.add(new Categorie(rs.getInt("id"), rs.getString("nume")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Categorie getById(int id) {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Categorie WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Categorie(rs.getInt("id"), rs.getString("nume"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteById(int id) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Categorie WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCategorie(Categorie categorie) {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE Categorie SET nume = ? WHERE id = ?")) {
            stmt.setString(1, categorie.getNume());
            stmt.setInt(2, categorie.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
