package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.Price;
import kz.miniland.minilandserver.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAllByIdIn(List<Long> priceIds);
}
