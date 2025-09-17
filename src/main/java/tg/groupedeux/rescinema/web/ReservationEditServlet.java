package tg.groupedeux.rescinema.web;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tg.groupedeux.rescinema.model.Reservation;
import tg.groupedeux.rescinema.model.ReservedSeat;
import tg.groupedeux.rescinema.model.Seance;
import tg.groupedeux.rescinema.service.ReservationService;

@WebServlet(urlPatterns = "/app/reservation/edit")
public class ReservationEditServlet extends HttpServlet {

    @PersistenceContext(unitName = "resCinemaPU")
    private EntityManager entityManager;

    @Inject
    private ReservationService reservationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Long userId = (Long) session.getAttribute("userId");
        Long reservationId = Long.valueOf(req.getParameter("id"));
        Reservation r = reservationService.findByIdForEdit(reservationId);
        if (r == null || r.getUser() == null || !r.getUser().getId().equals(userId)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Seance seance = r.getSeance();
        int rows = seance.getSalle().getNumRows();
        int seatsPerRow = seance.getSalle().getSeatsPerRow();

        // Sièges occupés par d'autres réservations de la même séance
        TypedQuery<ReservedSeat> q = entityManager.createQuery(
                "SELECT rs FROM ReservedSeat rs WHERE rs.seance.id = :sid AND rs.reservation.id <> :rid",
                ReservedSeat.class);
        q.setParameter("sid", seance.getId());
        q.setParameter("rid", r.getId());
        List<ReservedSeat> reserved = q.getResultList();

        Set<String> occupiedKeys = new HashSet<>();
        for (ReservedSeat rs : reserved) {
            occupiedKeys.add(rs.getRowNumber() + "-" + rs.getSeatNumber());
        }

        // Sièges actuellement sélectionnés dans cette réservation
        Set<String> selectedKeys = new HashSet<>();
        for (ReservedSeat rs : r.getReservedSeats()) {
            selectedKeys.add(rs.getRowNumber() + "-" + rs.getSeatNumber());
        }

        req.setAttribute("rows", rows);
        req.setAttribute("seatsPerRow", seatsPerRow);
        req.setAttribute("occupiedKeys", occupiedKeys);
        req.setAttribute("selectedKeys", selectedKeys);
        req.setAttribute("reservationId", r.getId());
        req.setAttribute("seanceId", seance.getId());
        req.getRequestDispatcher("/WEB-INF/jsp/app/reservation-edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Long userId = (Long) session.getAttribute("userId");
        Long reservationId = Long.valueOf(req.getParameter("reservationId"));
        String[] seats = req.getParameterValues("seats");
        java.util.List<int[]> parsed = new java.util.ArrayList<>();
        if (seats != null) {
            for (String s : seats) {
                String[] parts = s.split("-");
                parsed.add(new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
            }
        }
        try {
            reservationService.updateReservationSeats(userId, reservationId, parsed);
            resp.sendRedirect(req.getContextPath() + "/app/mes-reservations");
        } catch (IllegalStateException | IllegalArgumentException ex) {
            req.setAttribute("error", ex.getMessage());
            // Recharger la page d'édition
            req.setAttribute("id", reservationId);
            doGet(req, resp);
        }
    }
}


