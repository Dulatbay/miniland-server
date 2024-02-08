package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Order> findByAuthorNameAndCreatedAtBetween(String authorName, LocalDateTime start, LocalDateTime end);
}
