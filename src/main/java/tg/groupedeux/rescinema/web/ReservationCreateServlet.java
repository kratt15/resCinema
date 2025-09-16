package tg.groupedeux.rescinema.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tg.groupedeux.rescinema.model.Reservation;
import tg.groupedeux.rescinema.service.ReservationService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/app/reserver")
public class ReservationCreateServlet extends HttpServlet {

    @Inject
    private ReservationService reservationService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Long userId = (Long) session.getAttribute("userId");
        Long seanceId = Long.valueOf(req.getParameter("seanceId"));
        String[] seats = req.getParameterValues("seats"); // format: row-seat
        List<int[]> parsed = new ArrayList<>();
        if (seats != null) {
            for (String s : seats) {
                String[] parts = s.split("-");
                parsed.add(new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
            }
        }
        try {
            Reservation r = reservationService.createReservation(userId, seanceId, parsed);
            resp.sendRedirect(req.getContextPath() + "/app/mes-reservations");
        } catch (IllegalStateException | IllegalArgumentException ex) {
            req.setAttribute("error", ex.getMessage());
            resp.sendRedirect(req.getContextPath() + "/app/seance?id=" + seanceId);
        }
    }
}
