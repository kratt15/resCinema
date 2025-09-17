package tg.groupedeux.rescinema.web.admin;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tg.groupedeux.rescinema.service.AdminService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@WebServlet(urlPatterns = "/admin/seances")
public class AdminSeanceServlet extends HttpServlet {

    @Inject
    private AdminService adminService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            Long id = Long.valueOf(req.getParameter("id"));
            req.setAttribute("seance", id == 0 ? new Object() : adminService.updateSeance(id, null, null)); // get or new
            req.setAttribute("films", adminService.listFilms());
            req.setAttribute("salles", adminService.listSalles());
            req.getRequestDispatcher("/WEB-INF/jsp/admin/seance-form.jsp").forward(req, resp);
        } else {
            req.setAttribute("seances", adminService.listSeances());
            req.getRequestDispatcher("/WEB-INF/jsp/admin/seances.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("create".equals(action)) {
            Long filmId = Long.valueOf(req.getParameter("filmId"));
            Long salleId = Long.valueOf(req.getParameter("salleId"));
            OffsetDateTime startTime = OffsetDateTime.parse(req.getParameter("startTime"));
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            adminService.createSeance(filmId, salleId, startTime, price);
        } else if ("update".equals(action)) {
            Long id = Long.valueOf(req.getParameter("id"));
            OffsetDateTime startTime = OffsetDateTime.parse(req.getParameter("startTime"));
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            adminService.updateSeance(id, startTime, price);
        } else if ("delete".equals(action)) {
            Long id = Long.valueOf(req.getParameter("id"));
            adminService.deleteSeance(id);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/seances");
    }
}
