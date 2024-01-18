package kz.miniland.minilandserver.entities;

import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "order", schema = "schema_miniland")
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

    @Column(name = "phone_number", nullable = false)
    private Boolean isPaid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "entered_date")
    private Date enteredDate;

    @Column(name = "full_time", nullable = false)
    private Long fullTime;

    @Column(name = "full_price", nullable = false)
    private Long fullPrice;

    @Column(name = "author_id", nullable = false)
    private String authorId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_sale", schema = "schema_miniland",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "sale_id")
    )
    private List<Sale> saleList;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_price", schema = "schema_miniland",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "price_id")
    )
    private List<Price> priceList;
}
