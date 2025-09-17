package tg.groupedeux.rescinema.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tg.groupedeux.rescinema.service.AdminService;
import tg.groupedeux.rescinema.service.SeanceService;

@WebServlet(urlPatterns = "/app/home")
public class AppHomeServlet extends HttpServlet {

    @Inject
    private SeanceService seanceService;

    @Inject
    private AdminService adminService;

    private static final DateTimeFormatter HUMAN_DT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filmIdStr = req.getParameter("filmId");
        String dateStr = req.getParameter("date");
        Long filmId = null;
        if (filmIdStr != null && !filmIdStr.isBlank()) {
            try { filmId = Long.valueOf(filmIdStr); } catch (NumberFormatException ignored) {}
        }
        LocalDate date = null;
        if (dateStr != null && !dateStr.isBlank()) {
            try { date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE); } catch (Exception ignored) {}
        } else {
            date = LocalDate.now();
        }

        req.setAttribute("films", adminService.listFilms());
        req.setAttribute("selectedFilmId", filmId);
        req.setAttribute("selectedDate", date != null ? date.format(DateTimeFormatter.ISO_LOCAL_DATE) : "");
        req.setAttribute("seances", seanceService.search(filmId, date));
        req.setAttribute("dtf", HUMAN_DT);
        req.getRequestDispatcher("/WEB-INF/jsp/app/home.jsp").forward(req, resp);
    }
}
