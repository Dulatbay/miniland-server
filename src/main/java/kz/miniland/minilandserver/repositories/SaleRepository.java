package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.Price;
import kz.miniland.minilandserver.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findSalesByEnabled(Boolean isEnabled);
}
