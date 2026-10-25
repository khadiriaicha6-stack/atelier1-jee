package ma.fstt.dao;

import ma.fstt.entities.Client;
import ma.fstt.service.IClientService;
import ma.fstt.util.DBConnection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClientDAO implements IClientService {

    @Inject
    private DBConnection db;

    @Override
    public void ajouter(Client c) {
        String sql = "INSERT INTO client(nom, prenom, email, telephone, adresse) VALUES (?,?,?,?,?)";
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getNom());
            ps.setString(2, c.getPrenom());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getTelephone());
            ps.setString(5, c.getAdresse());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void modifier(Client c) {
        String sql = "UPDATE client SET nom=?, prenom=?, email=?, telephone=?, adresse=? WHERE id=?";
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getNom());
            ps.setString(2, c.getPrenom());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getTelephone());
            ps.setString(5, c.getAdresse());
            ps.setInt(6, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void supprimer(int id) {
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement("DELETE FROM client WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Client trouverParId(int id) {
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement("SELECT * FROM client WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    @Override
    public List<Client> lister() {
        List<Client> list = new ArrayList<>();
        try (Connection cn = db.getConnection(); Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM client ORDER BY id")) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    private Client map(ResultSet rs) throws SQLException {
        return new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
                rs.getString("email"), rs.getString("telephone"), rs.getString("adresse"));
    }
}