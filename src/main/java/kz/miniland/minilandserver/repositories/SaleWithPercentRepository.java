package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.SaleWithPercent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleWithPercentRepository extends JpaRepository<SaleWithPercent,Long> {

    List<SaleWithPercent> getSaleWithPercentByEnabled(Boolean enabled);

}
