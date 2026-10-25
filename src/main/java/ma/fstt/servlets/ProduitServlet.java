package ma.fstt.servlets;

import ma.fstt.dao.ProduitDAO;
import ma.fstt.entities.Produit;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/produits")
public class ProduitServlet extends HttpServlet {

    @Inject
    private ProduitDAO produitDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.setAttribute("produit", new Produit());
                req.getRequestDispatcher("/WEB-INF/views/produit-form.jsp").forward(req, resp);
                break;
            case "edit":
                int idEdit = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("produit", produitDAO.trouverParId(idEdit));
                req.getRequestDispatcher("/WEB-INF/views/produit-form.jsp").forward(req, resp);
                break;
            case "delete":
                try {
                    produitDAO.supprimer(Integer.parseInt(req.getParameter("id")));
                } catch (RuntimeException e) {
                    // product used in an order line: cannot delete
                }
                resp.sendRedirect(req.getContextPath() + "/produits");
                break;
            default:
                req.setAttribute("produits", produitDAO.lister());
                req.getRequestDispatcher("/WEB-INF/views/produit-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Produit p = new Produit();
        String id = req.getParameter("id");
        p.setDesignation(req.getParameter("designation"));
        p.setPrix(Double.parseDouble(req.getParameter("prix")));
        p.setQuantiteStock(Integer.parseInt(req.getParameter("quantiteStock")));

        if (id == null || id.isEmpty() || id.equals("0")) {
            produitDAO.ajouter(p);
        } else {
            p.setId(Integer.parseInt(id));
            produitDAO.modifier(p);
        }
        resp.sendRedirect(req.getContextPath() + "/produits");
    }
}
