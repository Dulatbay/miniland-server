package kz.miniland.minilandserver.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "abonement_order", schema = "schema_miniland")
public class AbonementOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "child_name", nullable = false)
    private String childName;

    @Column(name = "child_age", nullable = false)
    private String childAge;

    @Column(name = "quantity",nullable = false)
    private int quantity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(columnDefinition = "integer", name = "abonement_id")
    private BaseAbonement baseAbonement;

}
