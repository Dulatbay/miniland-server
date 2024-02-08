package kz.miniland.minilandserver.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "order_master_class", schema = "schema_miniland")
public class OrderMasterClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(columnDefinition = "integer", name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(columnDefinition = "integer", name = "master_class_id")
    private MasterClass masterClass;

    @Column(name = "added_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime addedAt;

    @Column(name = "author_name", nullable = false)
    private String authorName;
}
