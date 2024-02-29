package kz.miniland.minilandserver.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "sale_percent", schema = "schema_miniland")
public class SaleWithPercent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", nullable = false)
    private String title;

    @Column(name = "percent", nullable = false)
    private Integer percent;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;









}
