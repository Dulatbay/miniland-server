package kz.miniland.minilandserver.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Table(name = "room_tariff", schema = "schema_miniland")
@Data
@Entity
public class RoomTariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "started_at", nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime startedAt;

    @Column(name = "finished_at", nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime finishedAt;

    @Column(name = "days", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<WeekDays> days;

    @Column(name = "first_price", nullable = false)
    private Double firstPrice;

    @Column(name = "penalty_per_hour")
    private Double penaltyPerHour;

    @Column(name = "penalty_per_half_hour", nullable = false)
    private Double penaltyPerHalfHour;

    @Column(name = "max_child", nullable = false)
    private Integer maxChild;

    @Column(name = "child_price", nullable = false)
    private Double childPrice;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
}
