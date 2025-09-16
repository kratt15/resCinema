package tg.groupedeux.rescinema.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import tg.groupedeux.rescinema.model.Film;
import tg.groupedeux.rescinema.model.Salle;
import tg.groupedeux.rescinema.model.Seance;

// import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Stateless
public class AdminService {

    @PersistenceContext(unitName = "resCinemaPU")
    private EntityManager entityManager;

    // Film CRUD
    public Film createFilm(String title, String description, Integer durationMinutes) {
        Film f = new Film();
        f.setTitle(title);
        f.setDescription(description);
        f.setDurationMinutes(durationMinutes);
        entityManager.persist(f);
        return f;
    }

    public Film updateFilm(Long id, String title, String description, Integer durationMinutes) {
        Film f = entityManager.find(Film.class, id);
        if (f == null) return null;
        f.setTitle(title);
        f.setDescription(description);
        f.setDurationMinutes(durationMinutes);
        return f;
    }

    public boolean deleteFilm(Long id) {
        Film f = entityManager.find(Film.class, id);
        if (f == null) return false;
        entityManager.remove(f);
        return true;
    }

    public List<Film> listFilms() {
        TypedQuery<Film> q = entityManager.createQuery("SELECT f FROM Film f ORDER BY f.title", Film.class);
        return q.getResultList();
    }

    // Salle CRUD
    public Salle createSalle(String name, int numRows, int seatsPerRow) {
        Salle s = new Salle();
        s.setName(name);
        s.setNumRows(numRows);
        s.setSeatsPerRow(seatsPerRow);
        entityManager.persist(s);
        return s;
    }

    public Salle updateSalle(Long id, String name, int numRows, int seatsPerRow) {
        Salle s = entityManager.find(Salle.class, id);
        if (s == null) return null;
        s.setName(name);
        s.setNumRows(numRows);
        s.setSeatsPerRow(seatsPerRow);
        return s;
    }

    public boolean deleteSalle(Long id) {
        Salle s = entityManager.find(Salle.class, id);
        if (s == null) return false;
        entityManager.remove(s);
        return true;
    }

    public List<Salle> listSalles() {
        TypedQuery<Salle> q = entityManager.createQuery("SELECT s FROM Salle s ORDER BY s.name", Salle.class);
        return q.getResultList();
    }

    // Seance CRUD
    public Seance createSeance(Long filmId, Long salleId, OffsetDateTime startTime, java.math.BigDecimal price) {
        Film film = entityManager.find(Film.class, filmId);
        Salle salle = entityManager.find(Salle.class, salleId);
        if (film == null || salle == null) throw new IllegalArgumentException("Film ou Salle introuvable");
        Seance seance = new Seance();
        seance.setFilm(film);
        seance.setSalle(salle);
        seance.setStartTime(startTime);
        seance.setPrice(price);
        entityManager.persist(seance);
        return seance;
    }

    public Seance updateSeance(Long seanceId, OffsetDateTime startTime, java.math.BigDecimal price) {
        Seance s = entityManager.find(Seance.class, seanceId);
        if (s == null) return null;
        s.setStartTime(startTime);
        s.setPrice(price);
        return s;
    }

    public boolean deleteSeance(Long id) {
        Seance s = entityManager.find(Seance.class, id);
        if (s == null) return false;
        entityManager.remove(s);
        return true;
    }

    public List<Seance> listSeances() {
        TypedQuery<Seance> q = entityManager.createQuery("SELECT s FROM Seance s JOIN FETCH s.film JOIN FETCH s.salle ORDER BY s.startTime", Seance.class);
        return q.getResultList();
    }
}
