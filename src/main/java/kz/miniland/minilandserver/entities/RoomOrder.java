package kz.miniland.minilandserver.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Table(name = "room_order", schema = "schema_miniland")
@Entity
@Data
public class RoomOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(columnDefinition = "integer", name = "room_tariff_id")
    private RoomTariff roomTariff;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "day_of_booking", nullable = false)
    private LocalDateTime dayOfBooking;

    @Temporal(TemporalType.DATE)
    @Column(name = "booked_day", nullable = false)
    private LocalDate bookedDay;

    @Column(name = "child_quentity", nullable = false)
    private Integer childQuentity;

    @Column(name = "full_price", nullable = false)
    private Double fullPrice;

    @Column(name = "full_time", nullable = false)
    private Long fullTime;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "client_phone_number", nullable = false)
    private String clientPhoneNumber;

    @Column(name = "started_at", nullable = false)
    private LocalTime startedAt;

    @Column(name = "finished_at", nullable = false)
    private LocalTime finishedAt;

    @Column(name = "finished", nullable = false)
    private boolean finished;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "paid", nullable = false)
    private boolean paid;
}
