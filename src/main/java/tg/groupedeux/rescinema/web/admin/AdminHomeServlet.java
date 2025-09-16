package tg.groupedeux.rescinema.web.admin;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tg.groupedeux.rescinema.service.AdminService;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet(urlPatterns = "/admin/home")
public class AdminHomeServlet extends HttpServlet {

    @Inject
    private AdminService adminService;

    private static final DateTimeFormatter HUMAN_DT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("films", adminService.listFilms());
        req.setAttribute("salles", adminService.listSalles());
        req.setAttribute("seances", adminService.listSeances());
        req.setAttribute("dtf", HUMAN_DT);
        req.getRequestDispatcher("/WEB-INF/jsp/admin/home.jsp").forward(req, resp);
    }
}
