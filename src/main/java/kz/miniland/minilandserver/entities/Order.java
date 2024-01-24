package kz.miniland.minilandserver.entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import kz.miniland.minilandserver.constants.ValueConstants;
import lombok.*;


@Entity
@Table(name = "order", schema = "schema_miniland")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "child_name", nullable = false)
    private String childName;

    @Column(name = "child_age", nullable = false)
    private Integer childAge;

    @Column(name = "parent_name", nullable = false)
    private String parentName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "full_time", nullable = false)
    private Long fullTime;

    @Column(name = "full_price", nullable = false)
    private Double fullPrice;

    @Column(name = "extra_time", nullable = false)
    private Long extraTime;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished;

    @Column(name = "author_id", nullable = false)
    private String authorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "integer",name = "sale_id")
    private Sale sale;
}
