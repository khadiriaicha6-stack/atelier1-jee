package ma.fstt.dao;

import ma.fstt.entities.Commande;
import ma.fstt.entities.LigneCommande;
import ma.fstt.entities.Produit;
import ma.fstt.service.ILigneCommandeService;
import ma.fstt.util.DBConnection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LigneCommandeDAO implements ILigneCommandeService {

    @Inject
    private DBConnection db;

    @Inject
    private ProduitDAO produitDAO;

    @Override
    public void ajouter(LigneCommande l) {
        String sql = "INSERT INTO ligne_commande(commande_id, produit_id, quantite, prix_unitaire) VALUES (?,?,?,?)";
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, l.getCommande().getId());
            ps.setInt(2, l.getProduit().getId());
            ps.setInt(3, l.getQuantite());
            ps.setDouble(4, l.getPrixUnitaire());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void modifier(LigneCommande l) {
        String sql = "UPDATE ligne_commande SET produit_id=?, quantite=?, prix_unitaire=? WHERE id=?";
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, l.getProduit().getId());
            ps.setInt(2, l.getQuantite());
            ps.setDouble(3, l.getPrixUnitaire());
            ps.setInt(4, l.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void supprimer(int id) {
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement("DELETE FROM ligne_commande WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public LigneCommande trouverParId(int id) {
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement("SELECT * FROM ligne_commande WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    @Override
    public List<LigneCommande> listerParCommande(int commandeId) {
        List<LigneCommande> list = new ArrayList<>();
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM ligne_commande WHERE commande_id=? ORDER BY id")) {
            ps.setInt(1, commandeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    private LigneCommande map(ResultSet rs) throws SQLException {
        LigneCommande l = new LigneCommande();
        l.setId(rs.getInt("id"));
        Commande c = new Commande();
        c.setId(rs.getInt("commande_id"));
        l.setCommande(c);
        Produit p = produitDAO.trouverParId(rs.getInt("produit_id"));
        l.setProduit(p);
        l.setQuantite(rs.getInt("quantite"));
        l.setPrixUnitaire(rs.getDouble("prix_unitaire"));
        return l;
    }
}