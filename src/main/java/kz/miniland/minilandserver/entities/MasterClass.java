package kz.miniland.minilandserver.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "master_class", schema = "schema_miniland")
public class MasterClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
}
