package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.Order;
import kz.miniland.minilandserver.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findAllByOrderByFullPriceDesc();
    List<Price> findAllByEnabledOrderByFullPriceDesc(Boolean isEnabled);
}
