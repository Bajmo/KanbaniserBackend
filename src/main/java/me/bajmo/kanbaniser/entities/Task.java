package me.bajmo.kanbaniser.entities;

import jakarta.persistence.*;
import lombok.*;
import me.bajmo.kanbaniser.models.ESection;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
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
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    private ESection ESection;

}
