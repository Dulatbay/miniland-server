package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.RoomOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomOrderRepository extends JpaRepository<RoomOrder, Long> {
    List<RoomOrder> getAllByBookedDayBetweenAndDeletedAndFinished(LocalDate start, LocalDate end, boolean deleted, boolean finished);

    List<RoomOrder> getAllByBookedDayBetweenAndDeletedIsFalse(LocalDate start, LocalDate end);

    List<RoomOrder> getAllByBookedDayBetweenAndDeletedIsFalseAndAuthorName(LocalDate start, LocalDate end, String username);

    List<RoomOrder> getAllByBookedDayAfter(LocalDate localDateTime);

    Optional<RoomOrder> findTopByBookedDayAfterAndDeletedAndFinishedOrderByStartedAt(LocalDate localDate, boolean deleted, boolean finished);
}
