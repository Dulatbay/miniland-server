package kz.miniland.minilandserver.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "price", schema = "schema_miniland")
@Getter
@Setter
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "full_time", nullable = false)
    private Long fullTime;
    @Column(name = "full_price", nullable = false)
    private Double fullPrice;
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

}
