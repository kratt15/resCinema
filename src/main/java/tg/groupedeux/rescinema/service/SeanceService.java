package tg.groupedeux.rescinema.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import tg.groupedeux.rescinema.model.ReservedSeat;
import tg.groupedeux.rescinema.model.Salle;
import tg.groupedeux.rescinema.model.Seance;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Stateless
public class SeanceService {

    @PersistenceContext(unitName = "resCinemaPU")
    private EntityManager entityManager;

    public List<Seance> search(Long filmId, LocalDate date) {
        String jpql = "SELECT s FROM Seance s JOIN FETCH s.film JOIN FETCH s.salle WHERE (:filmId IS NULL OR s.film.id = :filmId) AND (:start IS NULL OR s.startTime >= :start) AND (:end IS NULL OR s.startTime < :end) ORDER BY s.startTime";
        TypedQuery<Seance> q = entityManager.createQuery(jpql, Seance.class);
        q.setParameter("filmId", filmId);
        if (date != null) {
            OffsetDateTime start = date.atStartOfDay().atOffset(ZoneOffset.UTC);
            OffsetDateTime end = start.plusDays(1);
            q.setParameter("start", start);
            q.setParameter("end", end);
        } else {
            q.setParameter("start", null);
            q.setParameter("end", null);
        }
        return q.getResultList();
    }

    public Map<String, Object> availability(Long seanceId) {
        Seance seance = entityManager.find(Seance.class, seanceId);
        if (seance == null) throw new IllegalArgumentException("Séance introuvable");
        Salle salle = seance.getSalle();
        int rows = salle.getNumRows();
        int seatsPerRow = salle.getSeatsPerRow();

        TypedQuery<ReservedSeat> q = entityManager.createQuery("SELECT r FROM ReservedSeat r WHERE r.seance.id = :sid", ReservedSeat.class);
        q.setParameter("sid", seanceId);
        List<ReservedSeat> reserved = q.getResultList();

        boolean[][] occupied = new boolean[rows][seatsPerRow];
        for (ReservedSeat rs : reserved) {
            int r = rs.getRowNumber() - 1;
            int c = rs.getSeatNumber() - 1;
            if (r >= 0 && r < rows && c >= 0 && c < seatsPerRow) {
                occupied[r][c] = true;
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("seatsPerRow", seatsPerRow);
        result.put("occupied", occupied);
        return result;
    }
}
