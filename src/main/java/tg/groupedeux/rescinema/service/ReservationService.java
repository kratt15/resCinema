package tg.groupedeux.rescinema.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
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

    public boolean updateReservationSeats(Long userId, Long reservationId, List<int[]> seats) {
        if (seats == null || seats.isEmpty()) {
            throw new IllegalArgumentException("Aucune place sélectionnée");
        }
        Reservation r = entityManager.find(Reservation.class, reservationId, LockModeType.PESSIMISTIC_WRITE);
        if (r == null) throw new IllegalArgumentException("Réservation introuvable");
        if (r.getUser() == null || !r.getUser().getId().equals(userId)) {
            throw new IllegalStateException("Accès interdit");
        }
        if (r.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Réservation annulée");
        }
        Seance seance = entityManager.find(Seance.class, r.getSeance().getId(), LockModeType.PESSIMISTIC_WRITE);

        // Construire les ensembles des sièges souhaités et actuels
        Set<String> desired = new HashSet<>();
        for (int[] s : seats) {
            desired.add(s[0] + "-" + s[1]);
        }
        Set<String> current = new HashSet<>();
        for (ReservedSeat rs : r.getReservedSeats()) {
            current.add(rs.getRowNumber() + "-" + rs.getSeatNumber());
        }
        // Si identiques, rien à faire
        if (desired.equals(current)) return true;

        // Sièges à ajouter et à retirer
        Set<String> toAdd = new HashSet<>(desired);
        toAdd.removeAll(current);
        Set<String> toRemove = new HashSet<>(current);
        toRemove.removeAll(desired);

        try {
            // Vérifier les conflits pour les nouveaux sièges avec d'autres réservations
            TypedQuery<ReservedSeat> q = entityManager.createQuery(
                "SELECT rs FROM ReservedSeat rs WHERE rs.seance.id = :sid AND rs.reservation.id <> :rid",
                ReservedSeat.class);
            q.setParameter("sid", seance.getId());
            q.setParameter("rid", r.getId());
            List<ReservedSeat> others = q.getResultList();
            Set<String> occupied = new HashSet<>();
            for (ReservedSeat o : others) {
                occupied.add(o.getRowNumber() + "-" + o.getSeatNumber());
            }
            for (String key : toAdd) {
                if (occupied.contains(key)) {
                    throw new IllegalStateException("La place " + key + " est déjà réservée.");
                }
            }

            // Supprimer uniquement les sièges à retirer
            if (!toRemove.isEmpty()) {
                List<ReservedSeat> copy = new ArrayList<>(r.getReservedSeats());
                for (ReservedSeat existing : copy) {
                    String key = existing.getRowNumber() + "-" + existing.getSeatNumber();
                    if (toRemove.contains(key)) {
                        entityManager.remove(existing);
                        r.getReservedSeats().remove(existing);
                    }
                }
                entityManager.flush(); // S'assure que les suppressions sont écrites avant les insertions
            }

            // Ajouter les nouveaux sièges
            for (String key : toAdd) {
                String[] parts = key.split("-");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                ReservedSeat rs = new ReservedSeat();
                rs.setReservation(r);
                rs.setSeance(seance);
                rs.setRowNumber(row);
                rs.setSeatNumber(col);
                entityManager.persist(rs);
                r.getReservedSeats().add(rs);
            }
            entityManager.flush();
            return true;
        } catch (PersistenceException ex) {
            throw new IllegalStateException("Conflit de réservation détecté, veuillez réessayer.", ex);
        }
    }

    public Reservation findById(Long id) {
        return entityManager.find(Reservation.class, id);
    }

    public Reservation findByIdForEdit(Long id) {
        try {
            TypedQuery<Reservation> q = entityManager.createQuery(
                "SELECT DISTINCT r FROM Reservation r " +
                "JOIN FETCH r.seance " +
                "JOIN FETCH r.seance.salle " +
                "LEFT JOIN FETCH r.reservedSeats " +
                "WHERE r.id = :id", Reservation.class);
            q.setParameter("id", id);
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Reservation> listByUser(Long userId) {
        // Requête simple sans JOIN FETCH pour éviter les problèmes d'alias
        TypedQuery<Reservation> q = entityManager.createQuery(
                "SELECT r FROM Reservation r WHERE r.user.id = :uid ORDER BY r.createdAt DESC",
                Reservation.class);
        q.setParameter("uid", userId);
        List<Reservation> reservations = q.getResultList();
        // Force le chargement des relations pour éviter LazyInitializationException
        for (Reservation r : reservations) {
            r.getSeance().getFilm().getTitle(); // Force load
            r.getSeance().getSalle().getName(); // Force load
        }
        return reservations;
    }
}
