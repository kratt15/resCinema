package tg.groupedeux.rescinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "salles", indexes = {
        @Index(name = "idx_salles_name", columnList = "name", unique = true)
})
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "num_rows", nullable = false)
    private Integer numRows;

    @Column(name = "seats_per_row", nullable = false)
    private Integer seatsPerRow;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getNumRows() { return numRows; }
    public void setNumRows(Integer numRows) { this.numRows = numRows; }

    public Integer getSeatsPerRow() { return seatsPerRow; }
    public void setSeatsPerRow(Integer seatsPerRow) { this.seatsPerRow = seatsPerRow; }
}
