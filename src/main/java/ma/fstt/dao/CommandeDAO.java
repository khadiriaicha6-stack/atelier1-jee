package ma.fstt.dao;

import ma.fstt.entities.Client;
import ma.fstt.entities.Commande;
import ma.fstt.service.ICommandeService;
import ma.fstt.util.DBConnection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CommandeDAO implements ICommandeService {

    @Inject
    private DBConnection db;

    @Inject
    private ClientDAO clientDAO;

    @Inject
    private LigneCommandeDAO ligneDAO;

    @Override
    public void ajouter(Commande c) {
        String sql = "INSERT INTO commande(date_commande, client_id) VALUES (?,?)";
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, c.getDateCommande());
            ps.setInt(2, c.getClient().getId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void modifier(Commande c) {
        String sql = "UPDATE commande SET date_commande=?, client_id=? WHERE id=?";
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setDate(1, c.getDateCommande());
            ps.setInt(2, c.getClient().getId());
            ps.setInt(3, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void supprimer(int id) {
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement("DELETE FROM commande WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Commande trouverParId(int id) {
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement("SELECT * FROM commande WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    @Override
    public List<Commande> lister() {
        List<Commande> list = new ArrayList<>();
        try (Connection cn = db.getConnection(); Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM commande ORDER BY id")) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    private Commande map(ResultSet rs) throws SQLException {
        Commande c = new Commande();
        c.setId(rs.getInt("id"));
        c.setDateCommande(rs.getDate("date_commande"));
        Client cl = clientDAO.trouverParId(rs.getInt("client_id"));
        c.setClient(cl);
        c.setLignes(ligneDAO.listerParCommande(c.getId()));
        return c;
    }
}