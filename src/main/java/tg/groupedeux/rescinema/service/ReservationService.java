package tg.groupedeux.rescinema.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import tg.groupedeux.rescinema.model.Reservation;
import tg.groupedeux.rescinema.model.ReservationStatus;
import tg.groupedeux.rescinema.model.ReservedSeat;
import tg.groupedeux.rescinema.model.Seance;
import tg.groupedeux.rescinema.model.User;

@Stateless
public class ReservationService {

    @PersistenceContext(unitName = "resCinemaPU")
    private EntityManager entityManager;

    public Reservation createReservation(Long userId, Long seanceId, List<int[]> seats) {
        if (seats == null || seats.isEmpty()) {
            throw new IllegalArgumentException("Aucune place sélectionnée");
        }
        User user = entityManager.find(User.class, userId);
        Seance seance = entityManager.find(Seance.class, seanceId);
        if (user == null || seance == null) {
            throw new IllegalArgumentException("Utilisateur ou Séance introuvable");
        }

        try {
            entityManager.lock(seance, LockModeType.PESSIMISTIC_WRITE);

            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setSeance(seance);
            entityManager.persist(reservation);

            List<ReservedSeat> created = new ArrayList<>();
            for (int[] seat : seats) {
                int row = seat[0];
                int col = seat[1];
                ReservedSeat rs = new ReservedSeat();
                rs.setReservation(reservation);
                rs.setSeance(seance);
                rs.setRowNumber(row);
                rs.setSeatNumber(col);
                entityManager.persist(rs);
                created.add(rs);
            }
            entityManager.flush();
            return reservation;
        } catch (PersistenceException ex) {
            throw new IllegalStateException("Conflit de réservation détecté, veuillez réessayer.", ex);
        }
    }

    public boolean cancelReservation(Long reservationId) {
        Reservation r = entityManager.find(Reservation.class, reservationId, LockModeType.PESSIMISTIC_WRITE);
        if (r == null) return false;
        r.setStatus(ReservationStatus.CANCELLED);
        entityManager.remove(r);
        return true;
    }

    public Reservation findById(Long id) {
        return entityManager.find(Reservation.class, id);
    }

    public List<Reservation> listByUser(Long userId) {
        TypedQuery<Reservation> q = entityManager.createQuery(
                "SELECT r FROM Reservation r JOIN FETCH r.seance s JOIN FETCH s.film JOIN FETCH s.salle WHERE r.user.id = :uid ORDER BY r.createdAt DESC",
                Reservation.class);
        q.setParameter("uid", userId);
        return q.getResultList();
    }
}
