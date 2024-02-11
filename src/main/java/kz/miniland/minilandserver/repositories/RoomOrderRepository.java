package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.RoomOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomOrderRepository extends JpaRepository<RoomOrder, Long> {
    List<RoomOrder> getAllByBookedDayBetween(LocalDate start, LocalDate end);

    List<RoomOrder> getAllByBookedDayAfter(LocalDate localDateTime);
}
