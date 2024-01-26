package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.Profit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProfitRepository extends JpaRepository<Profit, Long> {
    List<Profit> findAllByCreateAtBetween(LocalDateTime start, LocalDateTime end);
}
