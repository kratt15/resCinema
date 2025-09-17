package tg.groupedeux.rescinema.web.admin;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tg.groupedeux.rescinema.model.Seance;
import tg.groupedeux.rescinema.service.AdminService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet(urlPatterns = "/admin/seances")
public class AdminSeanceServlet extends HttpServlet {

    @Inject
    private AdminService adminService;

    private static final DateTimeFormatter HUMAN_DT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());
    private static final DateTimeFormatter DT_LOCAL = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            Long id = Long.valueOf(req.getParameter("id"));
            if (id > 0) {
                // Récupérer la séance existante pour édition
                Seance seance = adminService.listSeances().stream()
                    .filter(s -> s.getId().equals(id))
                    .findFirst().orElse(null);
                req.setAttribute("seance", seance);
                LocalDateTime startLocal = seance != null && seance.getStartTime() != null
                    ? seance.getStartTime().atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
                    : LocalDateTime.now();
                req.setAttribute("startLocal", DT_LOCAL.format(startLocal));
            } else {
                req.setAttribute("startLocal", DT_LOCAL.format(LocalDateTime.now()));
            }
            req.setAttribute("films", adminService.listFilms());
            req.setAttribute("salles", adminService.listSalles());
            req.getRequestDispatcher("/WEB-INF/jsp/admin/seance-form.jsp").forward(req, resp);
        } else {
            req.setAttribute("seances", adminService.listSeances());
            req.setAttribute("dtf", HUMAN_DT);
            req.getRequestDispatcher("/WEB-INF/jsp/admin/seances.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("create".equals(action)) {
            Long filmId = Long.valueOf(req.getParameter("filmId"));
            Long salleId = Long.valueOf(req.getParameter("salleId"));
            LocalDateTime localStart = LocalDateTime.parse(req.getParameter("startTime"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            OffsetDateTime startTime = localStart.atZone(ZoneId.systemDefault()).toOffsetDateTime();
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            adminService.createSeance(filmId, salleId, startTime, price);
        } else if ("update".equals(action)) {
            Long id = Long.valueOf(req.getParameter("id"));
            LocalDateTime localStart = LocalDateTime.parse(req.getParameter("startTime"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            OffsetDateTime startTime = localStart.atZone(ZoneId.systemDefault()).toOffsetDateTime();
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            adminService.updateSeance(id, startTime, price);
        } else if ("delete".equals(action)) {
            Long id = Long.valueOf(req.getParameter("id"));
            adminService.deleteSeance(id);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/seances");
    }
}
