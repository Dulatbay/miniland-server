package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.entities.OrderMasterClass;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.repositories.MasterClassRepository;
import kz.miniland.minilandserver.repositories.OrderMasterClassRepository;
import kz.miniland.minilandserver.repositories.OrderRepository;
import kz.miniland.minilandserver.services.MasterClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static kz.miniland.minilandserver.constants.ValueConstants.ZONE_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MasterClassServiceImpl implements MasterClassService {
    private final OrderMasterClassRepository orderMasterClassRepository;
    private final OrderRepository orderRepository;
    private final MasterClassRepository masterClassRepository;

    @Override
    public void addMasterClassToOrder(String token, Long orderId, Long masterClassId) {
        var masterClass = masterClassRepository.findById(masterClassId)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Master class doesn't exist"));

        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Order doesn't exist"));

        OrderMasterClass orderMasterClass = new OrderMasterClass();
        orderMasterClass.setAuthorName(token);
        orderMasterClass.setAddedAt(LocalDateTime.now(ZONE_ID));
        orderMasterClass.setOrder(order);
        orderMasterClass.setMasterClass(masterClass);

        orderMasterClassRepository.save(orderMasterClass);
    }
}
