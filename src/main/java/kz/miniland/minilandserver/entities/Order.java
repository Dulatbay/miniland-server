package kz.miniland.minilandserver.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "order", schema = "schema_miniland")
@Setter
@Getter
public class Order  {
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

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Column(name = "full_time", nullable = false)
    private Long fullTime;

    @Column(name = "full_price", nullable = false)
    private Double fullPrice;

    @Column(name = "extra_time", nullable = false)
    private Long extraTime;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(columnDefinition = "integer", name = "sale_id")
    private Sale sale;
}
