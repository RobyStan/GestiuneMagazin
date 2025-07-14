import java.sql.*;
import java.util.*;

public class ProdusAlimentarService {
    private final Connection conn;

    public ProdusAlimentarService() throws SQLException {
        this.conn = DbConnection.getInstance();
    }

    public void insertProdusAlimentar(ProdusAlimentar produs) {
        try {
            conn.setAutoCommit(false);

            String sqlProdus = "INSERT INTO Produs (cod, nume, pret, stoc, categorie_id, distribuitor_id) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmtProdus = conn.prepareStatement(sqlProdus)) {
                stmtProdus.setString(1, produs.getCod());
                stmtProdus.setString(2, produs.getNume());
                stmtProdus.setDouble(3, produs.getPret());
                stmtProdus.setInt(4, produs.getStoc());
                stmtProdus.setInt(5, produs.getCategorie().getId());
                stmtProdus.setInt(6, produs.getDistribuitor().getId());
                stmtProdus.executeUpdate();
            }

            String sqlAlimentar = "INSERT INTO ProdusAlimentar (cod, dataExpirare) VALUES (?, ?)";
            try (PreparedStatement stmtAlimentar = conn.prepareStatement(sqlAlimentar)) {
                stmtAlimentar.setString(1, produs.getCod());
                stmtAlimentar.setDate(2, java.sql.Date.valueOf(produs.getDataExpirare()));  // prefix complet
                stmtAlimentar.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public List<ProdusAlimentar> getProdusAlimentar(List<Categorie> categorii, List<Distribuitor> distribuitori) {
        List<ProdusAlimentar> list = new ArrayList<>();
        String sql = "SELECT p.*, a.dataExpirare FROM Produs p JOIN ProdusAlimentar a ON p.cod = a.cod";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int categorieId = rs.getInt("categorie_id");
                int distribuitorId = rs.getInt("distribuitor_id");

                Categorie c = categorii.stream().filter(cat -> cat.getId() == categorieId).findFirst().orElse(null);
                Distribuitor d = distribuitori.stream().filter(dist -> dist.getId() == distribuitorId).findFirst().orElse(null);

                ProdusAlimentar p = new ProdusAlimentar(
                        rs.getString("cod"),
                        rs.getString("nume"),
                        rs.getDouble("pret"),
                        rs.getInt("stoc"),
                        c,
                        d,
                        rs.getDate("dataExpirare").toString() // java.sql.Date
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateProdusAlimentar(ProdusAlimentar produs) {
        try {
            conn.setAutoCommit(false);

            String sqlProdus = "UPDATE Produs SET nume = ?, pret = ?, stoc = ?, categorie_id = ?, distribuitor_id = ? WHERE cod = ?";
            try (PreparedStatement stmtProdus = conn.prepareStatement(sqlProdus)) {
                stmtProdus.setString(1, produs.getNume());
                stmtProdus.setDouble(2, produs.getPret());
                stmtProdus.setInt(3, produs.getStoc());
                stmtProdus.setInt(4, produs.getCategorie().getId());
                stmtProdus.setInt(5, produs.getDistribuitor().getId());
                stmtProdus.setString(6, produs.getCod());
                stmtProdus.executeUpdate();
            }

            String sqlAlimentar = "UPDATE ProdusAlimentar SET dataExpirare = ? WHERE cod = ?";
            try (PreparedStatement stmtAlimentar = conn.prepareStatement(sqlAlimentar)) {
                stmtAlimentar.setDate(1, java.sql.Date.valueOf(produs.getDataExpirare()));  // prefix complet
                stmtAlimentar.setString(2, produs.getCod());
                stmtAlimentar.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public void deleteProdusAlimentar(String cod) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Produs WHERE cod = ?")) {
            stmt.setString(1, cod);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
