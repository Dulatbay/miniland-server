package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.Order;
import kz.miniland.minilandserver.entities.OrderMasterClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMasterClassRepository extends JpaRepository<OrderMasterClass, Long> {
    List<OrderMasterClass> findAllByOrder(Order order);
}
