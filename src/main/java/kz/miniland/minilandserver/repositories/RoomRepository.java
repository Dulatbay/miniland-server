package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> { }
