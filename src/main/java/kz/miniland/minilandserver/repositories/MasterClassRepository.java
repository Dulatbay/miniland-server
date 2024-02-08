package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.MasterClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterClassRepository extends JpaRepository<MasterClass, Long> {
}
