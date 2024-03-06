package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.BaseAbonement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseAbonementRepository extends JpaRepository<BaseAbonement, Long> {

    List<BaseAbonement> getBaseAbonementsByEnabled(Boolean enabled);

}
