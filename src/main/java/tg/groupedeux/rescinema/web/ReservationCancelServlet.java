package tg.groupedeux.rescinema.web;

import java.io.IOException;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tg.groupedeux.rescinema.model.Reservation;
import tg.groupedeux.rescinema.service.ReservationService;

@WebServlet(urlPatterns = "/app/reservation/cancel")
public class ReservationCancelServlet extends HttpServlet {

    @Inject
    private ReservationService reservationService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Long userId = (Long) session.getAttribute("userId");
        Long reservationId = null;
        try { reservationId = Long.valueOf(req.getParameter("id")); } catch (Exception ignored) {}
        if (reservationId == null) {
            resp.sendRedirect(req.getContextPath() + "/app/mes-reservations");
            return;
        }

        Reservation r = reservationService.findById(reservationId);
        if (r == null || r.getUser() == null || !r.getUser().getId().equals(userId)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        reservationService.cancelReservation(reservationId);
        resp.sendRedirect(req.getContextPath() + "/app/mes-reservations");
    }
}


