package kz.miniland.minilandserver.repositories;

import kz.miniland.minilandserver.entities.AbonementOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AbonementOrderRepository extends JpaRepository<AbonementOrder, Long> {

    List<AbonementOrder> getAbonementOrdersByEnabledIsTrue();

    Optional<AbonementOrder> findAbonementOrderByPhoneNumberAndEnabledIsTrue(String phoneNumber);

}
