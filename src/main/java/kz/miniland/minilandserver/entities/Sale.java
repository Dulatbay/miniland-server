package kz.miniland.minilandserver.entities;

import jakarta.persistence.*;
import org.aspectj.weaver.ast.Or;

import java.util.List;

@Entity
@Table(name = "sale", schema = "schema_sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "full_time", nullable = false)
    private Long fullTime;
    @Column(name = "full_price", nullable = false)
    private Long fullPrice;
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Order> orderList;

}
