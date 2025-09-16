package tg.groupedeux.rescinema.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tg.groupedeux.rescinema.service.SeanceService;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet(urlPatterns = "/app/home")
public class AppHomeServlet extends HttpServlet {

    @Inject
    private SeanceService seanceService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("seances", seanceService.search(null, LocalDate.now()));
        req.getRequestDispatcher("/WEB-INF/jsp/app/home.jsp").forward(req, resp);
    }
}
