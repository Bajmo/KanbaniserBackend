package me.bajmo.kanbaniser.entities;

import jakarta.persistence.*;
import lombok.*;
import me.bajmo.kanbaniser.utils.Section;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "createdBy_id", nullable = false)
    private User createdBy;
    @Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(name = "board_id") // Adjust the column name as needed
    private Board board;
    private Section section;

}
