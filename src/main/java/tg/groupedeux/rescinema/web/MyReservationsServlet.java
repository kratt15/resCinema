package tg.groupedeux.rescinema.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tg.groupedeux.rescinema.service.ReservationService;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet(urlPatterns = "/app/mes-reservations")
public class MyReservationsServlet extends HttpServlet {

    @Inject
    private ReservationService reservationService;

    private static final DateTimeFormatter HUMAN_DT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Long userId = (Long) session.getAttribute("userId");
        req.setAttribute("reservations", reservationService.listByUser(userId));
        req.setAttribute("dtf", HUMAN_DT);
        req.getRequestDispatcher("/WEB-INF/jsp/app/my-reservations.jsp").forward(req, resp);
    }
}
