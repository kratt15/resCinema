package tg.groupedeux.rescinema.web.admin;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tg.groupedeux.rescinema.model.Salle;
import tg.groupedeux.rescinema.service.AdminService;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/salles")
public class AdminSalleServlet extends HttpServlet {

    @Inject
    private AdminService adminService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            Long id = Long.valueOf(req.getParameter("id"));
            req.setAttribute("salle", adminService.updateSalle(id, null, 0, 0)); // get
            req.getRequestDispatcher("/WEB-INF/jsp/admin/salle-form.jsp").forward(req, resp);
        } else {
            req.setAttribute("salles", adminService.listSalles());
            req.getRequestDispatcher("/WEB-INF/jsp/admin/salles.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String name = req.getParameter("name");
        int numRows = Integer.parseInt(req.getParameter("numRows"));
        int seatsPerRow = Integer.parseInt(req.getParameter("seatsPerRow"));

        if ("create".equals(action)) {
            adminService.createSalle(name, numRows, seatsPerRow);
        } else if ("update".equals(action)) {
            Long id = Long.valueOf(req.getParameter("id"));
            adminService.updateSalle(id, name, numRows, seatsPerRow);
        } else if ("delete".equals(action)) {
            Long id = Long.valueOf(req.getParameter("id"));
            adminService.deleteSalle(id);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/salles");
    }
}
