package tg.groupedeux.rescinema.web.admin;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tg.groupedeux.rescinema.model.Film;
import tg.groupedeux.rescinema.service.AdminService;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/films")
public class AdminFilmServlet extends HttpServlet {

    @Inject
    private AdminService adminService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            Long id = Long.valueOf(req.getParameter("id"));
            if (id > 0) {
                Film film = adminService.listFilms().stream()
                    .filter(f -> f.getId().equals(id))
                    .findFirst().orElse(null);
                req.setAttribute("film", film);
            }
            req.getRequestDispatcher("/WEB-INF/jsp/admin/film-form.jsp").forward(req, resp);
        } else {
            req.setAttribute("films", adminService.listFilms());
            req.getRequestDispatcher("/WEB-INF/jsp/admin/films.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Integer duration = req.getParameter("duration") != null ? Integer.valueOf(req.getParameter("duration")) : null;

        if ("create".equals(action)) {
            adminService.createFilm(title, description, duration);
        } else if ("update".equals(action)) {
            Long id = Long.valueOf(req.getParameter("id"));
            adminService.updateFilm(id, title, description, duration);
        } else if ("delete".equals(action)) {
            Long id = Long.valueOf(req.getParameter("id"));
            adminService.deleteFilm(id);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/films");
    }
}
