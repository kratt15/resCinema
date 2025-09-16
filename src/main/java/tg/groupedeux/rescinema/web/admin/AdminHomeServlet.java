package tg.groupedeux.rescinema.web.admin;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tg.groupedeux.rescinema.service.AdminService;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/home")
public class AdminHomeServlet extends HttpServlet {

    @Inject
    private AdminService adminService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("films", adminService.listFilms());
        req.setAttribute("salles", adminService.listSalles());
        req.setAttribute("seances", adminService.listSeances());
        req.getRequestDispatcher("/WEB-INF/jsp/admin/home.jsp").forward(req, resp);
    }
}
