package kz.miniland.minilandserver.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "abonement", schema = "schema_miniland")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseAbonement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "full_price", nullable = false)
    private Double fullPrice;

    @Column(name = "fill_time", nullable = false)
    private Long fullTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "quantity",nullable = false)
    private int quantity;
}
