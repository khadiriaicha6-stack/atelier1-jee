package ma.fstt.servlets;

import ma.fstt.dao.ClientDAO;
import ma.fstt.dao.CommandeDAO;
import ma.fstt.dao.LigneCommandeDAO;
import ma.fstt.dao.ProduitDAO;
import ma.fstt.entities.Commande;
import ma.fstt.entities.LigneCommande;
import ma.fstt.entities.Produit;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet("/commandes")
public class CommandeServlet extends HttpServlet {

    @Inject
    private CommandeDAO commandeDAO;

    @Inject
    private ClientDAO clientDAO;

    @Inject
    private ProduitDAO produitDAO;

    @Inject
    private LigneCommandeDAO ligneDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.setAttribute("commande", new Commande());
                req.setAttribute("today", LocalDate.now().toString());
                req.setAttribute("clients", clientDAO.lister());
                req.getRequestDispatcher("/WEB-INF/views/commande-form.jsp").forward(req, resp);
                break;
            case "edit":
                int idEdit = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("commande", commandeDAO.trouverParId(idEdit));
                req.setAttribute("clients", clientDAO.lister());
                req.getRequestDispatcher("/WEB-INF/views/commande-form.jsp").forward(req, resp);
                break;
            case "view":
                int idView = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("commande", commandeDAO.trouverParId(idView));
                req.setAttribute("produits", produitDAO.lister());
                req.getRequestDispatcher("/WEB-INF/views/commande-detail.jsp").forward(req, resp);
                break;
            case "delete":
                commandeDAO.supprimer(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/commandes");
                break;
            case "deleteLine":
                int ligneId = Integer.parseInt(req.getParameter("ligneId"));
                int cmdId = Integer.parseInt(req.getParameter("id"));
                ligneDAO.supprimer(ligneId);
                resp.sendRedirect(req.getContextPath() + "/commandes?action=view&id=" + cmdId);
                break;
            default:
                req.setAttribute("commandes", commandeDAO.lister());
                req.getRequestDispatcher("/WEB-INF/views/commande-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("addLine".equals(action)) {
            int commandeId = Integer.parseInt(req.getParameter("commandeId"));
            int produitId = Integer.parseInt(req.getParameter("produitId"));
            int quantite = Integer.parseInt(req.getParameter("quantite"));

            Produit p = produitDAO.trouverParId(produitId);
            Commande cmd = new Commande();
            cmd.setId(commandeId);

            LigneCommande l = new LigneCommande();
            l.setCommande(cmd);
            l.setProduit(p);
            l.setQuantite(quantite);
            l.setPrixUnitaire(p.getPrix());
            ligneDAO.ajouter(l);

            resp.sendRedirect(req.getContextPath() + "/commandes?action=view&id=" + commandeId);
            return;
        }

        // action "save": create or update the commande
        String id = req.getParameter("id");
        Commande c = new Commande();
        c.setDateCommande(Date.valueOf(req.getParameter("dateCommande")));
        c.setClient(clientDAO.trouverParId(Integer.parseInt(req.getParameter("clientId"))));

        if (id == null || id.isEmpty() || id.equals("0")) {
            commandeDAO.ajouter(c);
        } else {
            c.setId(Integer.parseInt(id));
            commandeDAO.modifier(c);
        }
        resp.sendRedirect(req.getContextPath() + "/commandes?action=view&id=" + c.getId());
    }
}
