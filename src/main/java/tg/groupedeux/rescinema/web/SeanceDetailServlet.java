package tg.groupedeux.rescinema.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tg.groupedeux.rescinema.service.SeanceService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@WebServlet(urlPatterns = "/app/seance")
public class SeanceDetailServlet extends HttpServlet {

    @Inject
    private SeanceService seanceService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Map<String, Object> availability = seanceService.availability(id);
        int rows = (Integer) availability.get("rows");
        int seatsPerRow = (Integer) availability.get("seatsPerRow");
        boolean[][] occupied = (boolean[][]) availability.get("occupied");
        Set<String> occupiedKeys = new HashSet<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < seatsPerRow; c++) {
                if (occupied[r][c]) occupiedKeys.add((r + 1) + "-" + (c + 1));
            }
        }
        req.setAttribute("rows", rows);
        req.setAttribute("seatsPerRow", seatsPerRow);
        req.setAttribute("occupiedKeys", occupiedKeys);
        req.setAttribute("seanceId", id);
        req.getRequestDispatcher("/WEB-INF/jsp/app/seance.jsp").forward(req, resp);
    }
}
