    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    public class ProdusService {

        private Connection conn;

        public ProdusService() throws SQLException {
            this.conn = DbConnection.getInstance();
        }

            public void insertProdus(Produs p, int categorieId, int distribuitorId) throws SQLException {
            String sql = "INSERT INTO produs(cod, nume, pret, stoc, data_expirare, categorie_id, distribuitor_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, p.getCod());
                stmt.setString(2, p.getNume());
                stmt.setDouble(3, p.getPret());
                stmt.setInt(4, p.getStoc());
                stmt.setInt(6, categorieId);
                stmt.setInt(7, distribuitorId);
                stmt.executeUpdate();
            }
        }

        public List<Produs> getProdus(List<Categorie> categorii, List<Distribuitor> distribuitori) throws SQLException {
            List<Produs> produse = new ArrayList<>();
            String sql = "SELECT * FROM produs";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String cod = rs.getString("cod");
                    String nume = rs.getString("nume");
                    double pret = rs.getDouble("pret");
                    int stoc = rs.getInt("stoc");
                    int categorieId = rs.getInt("categorie_id");
                    int distribuitorId = rs.getInt("distribuitor_id");

                    Categorie categorie = categorii.stream()
                            .filter(c -> c.getId() == categorieId)
                            .findFirst()
                            .orElse(null);

                    Distribuitor distribuitor = distribuitori.stream()
                            .filter(d -> d.getId() == distribuitorId)
                            .findFirst()
                            .orElse(null);

                    Produs p = new Produs(cod, nume, pret, stoc, categorie, distribuitor);
                    produse.add(p);
                }
            }

            return produse;
        }

        public void updateProdus(Produs p, int categorieId, int distribuitorId) throws SQLException {
            String sql = "UPDATE produs SET nume = ?, pret = ?, stoc = ?, categorie_id = ?, distribuitor_id = ? WHERE cod = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, p.getNume());
                stmt.setDouble(2, p.getPret());
                stmt.setInt(3, p.getStoc());
                stmt.setInt(5, categorieId);
                stmt.setInt(6, distribuitorId);
                stmt.setString(7, p.getCod());
                stmt.executeUpdate();
            }
        }

        public void deleteProdus(String cod) throws SQLException {
            String sql = "DELETE FROM produs WHERE cod = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, cod);
                stmt.executeUpdate();
            }
        }
        public boolean produsExista(String cod) {
            String sql = "SELECT COUNT(*) FROM produs WHERE cod = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, cod);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }


    }
