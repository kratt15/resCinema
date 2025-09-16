package tg.groupedeux.rescinema.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "seances", indexes = {
        @Index(name = "idx_seances_film", columnList = "film_id"),
        @Index(name = "idx_seances_salle", columnList = "salle_id"),
        @Index(name = "idx_seances_datetime", columnList = "start_time")
})
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_id")
    private Salle salle;

    @Column(name = "start_time", nullable = false)
    private OffsetDateTime startTime;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Version
    private Long version;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }

    public Salle getSalle() { return salle; }
    public void setSalle(Salle salle) { this.salle = salle; }

    public OffsetDateTime getStartTime() { return startTime; }
    public void setStartTime(OffsetDateTime startTime) { this.startTime = startTime; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}
