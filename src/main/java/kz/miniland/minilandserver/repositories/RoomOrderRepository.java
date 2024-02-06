package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.RoomOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomOrderRepository extends JpaRepository<RoomOrder, Long> {
    List<RoomOrder> getAllByBookedDayBetween(LocalDateTime start, LocalDateTime end);
    List<RoomOrder> getAllByBookedDayAfter(LocalDateTime localDateTime);
}
