package dk.kinoxp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "theaters")
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int rows;
    private int seatsPerRow;

    // Cowboy rows = first 2 rows (cheaper)
    // Sofa rows = last 2 rows in large theater (more expensive)
    private int cowboyRowCount = 2;
    private int sofaRowCount;   // 0 for small theater, 2 for large

    public Theater() {}

    public int totalSeats() {
        return rows * seatsPerRow;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getRows() { return rows; }
    public void setRows(int rows) { this.rows = rows; }

    public int getSeatsPerRow() { return seatsPerRow; }
    public void setSeatsPerRow(int seatsPerRow) { this.seatsPerRow = seatsPerRow; }

    public int getCowboyRowCount() { return cowboyRowCount; }
    public void setCowboyRowCount(int cowboyRowCount) { this.cowboyRowCount = cowboyRowCount; }

    public int getSofaRowCount() { return sofaRowCount; }
    public void setSofaRowCount(int sofaRowCount) { this.sofaRowCount = sofaRowCount; }
}
