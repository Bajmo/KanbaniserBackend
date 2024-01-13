package me.bajmo.kanbaniser.models;

import jakarta.persistence.*;
import lombok.*;
import me.bajmo.kanbaniser.models.ERole;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }

}
