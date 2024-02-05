package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.RoomTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomTariff, Long> { }
