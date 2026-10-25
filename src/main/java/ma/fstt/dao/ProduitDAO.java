package ma.fstt.dao;

import ma.fstt.entities.Produit;
import ma.fstt.service.IProduitService;
import ma.fstt.util.DBConnection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProduitDAO implements IProduitService {

    @Inject
    private DBConnection db;

    @Override
    public void ajouter(Produit p) {
        String sql = "INSERT INTO produit(designation, prix, quantite_stock) VALUES (?,?,?)";
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getDesignation());
            ps.setDouble(2, p.getPrix());
            ps.setInt(3, p.getQuantiteStock());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void modifier(Produit p) {
        String sql = "UPDATE produit SET designation=?, prix=?, quantite_stock=? WHERE id=?";
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getDesignation());
            ps.setDouble(2, p.getPrix());
            ps.setInt(3, p.getQuantiteStock());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void supprimer(int id) {
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement("DELETE FROM produit WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Produit trouverParId(int id) {
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement("SELECT * FROM produit WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    @Override
    public List<Produit> lister() {
        List<Produit> list = new ArrayList<>();
        try (Connection cn = db.getConnection(); Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM produit ORDER BY id")) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    private Produit map(ResultSet rs) throws SQLException {
        return new Produit(rs.getInt("id"), rs.getString("designation"),
                rs.getDouble("prix"), rs.getInt("quantite_stock"));
    }
}
