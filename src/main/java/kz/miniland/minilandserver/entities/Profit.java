package kz.miniland.minilandserver.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;

import static kz.miniland.minilandserver.constants.ValueConstants.ZONE_ID;

@Entity
@Data
@Table(name = "profit", schema = "schema_miniland")
public class Profit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String reason;

    @Column(name = "value", nullable = false)
    private Double profit;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfitTypes type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_at")
    private LocalDateTime createAt;

    @PrePersist
    public void prePersist(){
        createAt = LocalDateTime.now(ZONE_ID);
    }


}
