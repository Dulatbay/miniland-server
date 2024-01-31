package kz.miniland.minilandserver.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Table(name = "room", schema = "schema_miniland")
@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="startedAt", nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime startedAt;

    @Column(name="finishedAt", nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime finishedAt;

    @Column(name="days", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<WeekDays> days;

    @Column(name="penalty_per_hour")
    private Double penaltyPerHour;

    @Column(name="penalty_per_half_hour", nullable = false)
    private Double penaltyPerHalfHour;
}
