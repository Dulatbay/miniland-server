package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.RoomTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTariffRepository extends JpaRepository<RoomTariff, Long> {
    List<RoomTariff> getAllByEnabled(Boolean enabled);
}
