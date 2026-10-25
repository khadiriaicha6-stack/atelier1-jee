package ma.fstt.servlets;

import ma.fstt.entities.Client;
import ma.fstt.service.IClientService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import ma.fstt.dao.ClientDAO;

@WebServlet("/clients")
public class ClientServlet extends HttpServlet {

    @Inject
    private ClientDAO clientDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.setAttribute("client", new Client());
                req.getRequestDispatcher("/WEB-INF/views/client-form.jsp").forward(req, resp);
                break;
            case "edit":
                int idEdit = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("client", clientDAO.trouverParId(idEdit));
                req.getRequestDispatcher("/WEB-INF/views/client-form.jsp").forward(req, resp);
                break;
            case "delete":
                clientDAO.supprimer(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/clients");
                break;
            default:
                req.setAttribute("clients", clientDAO.lister());
                req.getRequestDispatcher("/WEB-INF/views/client-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Client c = new Client();
        String id = req.getParameter("id");
        c.setNom(req.getParameter("nom"));
        c.setPrenom(req.getParameter("prenom"));
        c.setEmail(req.getParameter("email"));
        c.setTelephone(req.getParameter("telephone"));
        c.setAdresse(req.getParameter("adresse"));

        if (id == null || id.isEmpty() || id.equals("0")) {
            clientDAO.ajouter(c);
        } else {
            c.setId(Integer.parseInt(id));
            clientDAO.modifier(c);
        }
        resp.sendRedirect(req.getContextPath() + "/clients");
    }
}