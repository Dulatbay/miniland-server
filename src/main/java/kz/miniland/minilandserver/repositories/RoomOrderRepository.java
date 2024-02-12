package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.RoomOrder;
import kz.miniland.minilandserver.entities.RoomTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomOrderRepository extends JpaRepository<RoomOrder, Long> {
    List<RoomOrder> getAllByBookedDayBetweenAndDeletedAndFinished(LocalDate start, LocalDate end, boolean deleted, boolean finished);
    List<RoomOrder> getAllByBookedDayAfter(LocalDate localDateTime);
    Optional<RoomOrder> findTopByBookedDayAfterAndDeletedAndFinishedOrderByStartedAt(LocalDate localDate, boolean deleted, boolean finished);
}
