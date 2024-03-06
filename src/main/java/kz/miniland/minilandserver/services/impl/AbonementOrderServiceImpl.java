package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.request.RequestCreateAbonementOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseAbonementOrderDto;
import kz.miniland.minilandserver.entities.AbonementOrder;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.mappers.AbonementOrderMapper;
import kz.miniland.minilandserver.repositories.AbonementOrderRepository;
import kz.miniland.minilandserver.repositories.BaseAbonementRepository;
import kz.miniland.minilandserver.services.AbonementOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static kz.miniland.minilandserver.constants.ValueConstants.ZONE_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AbonementOrderServiceImpl implements AbonementOrderService {

    private final AbonementOrderRepository abonementOrderRepository;
    private final AbonementOrderMapper abonementOrderMapper;
    private final BaseAbonementRepository baseAbonementRepository;

    @Override
    public List<ResponseAbonementOrderDto> getAllAbonementOrders() {

        var abonementOrders = abonementOrderRepository.getAbonementOrdersByEnabledIsTrue();

        return abonementOrderMapper.toDTO(abonementOrders);

    }

    @Override
    public void createAbonementOrder(RequestCreateAbonementOrderDto requestCreateAbonementOrderDto) {

        //todo: can user create a multiple abonements?

        var abonementOrder = AbonementOrder.builder()
                .clientName(requestCreateAbonementOrderDto.getClientName())
                .phoneNumber(requestCreateAbonementOrderDto.getPhoneNumber())
                .childName(requestCreateAbonementOrderDto.getChildName())
                .childAge(requestCreateAbonementOrderDto.getChildAge())
                .quantity(requestCreateAbonementOrderDto.getQuantity())
                .createdAt(LocalDateTime.now(ZONE_ID))
                .baseAbonement(
                        baseAbonementRepository
                                .findById(requestCreateAbonementOrderDto.getBaseAbonementId())
                                .orElseThrow(() ->  new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "BaseAbonement class doesn't exist"))
                )
                .enabled(true)
                .build();

        log.info("Created abonement order: {}", abonementOrder);

        abonementOrderRepository.save(abonementOrder);

    }

    @Override
    public List<ResponseAbonementOrderDto> getAbonementOrdersByPhoneNumber(String phoneNumber) {

        if(phoneNumber == null || phoneNumber.trim().isEmpty()){

            log.error("Phone number can't be empty");

            throw new IllegalArgumentException("Phone number can't be empty");

        }

        var abonementOrder = abonementOrderRepository
                .findAbonementOrdersByPhoneNumberAndEnabledIsTrue(phoneNumber);

        return abonementOrderMapper.toDTO(abonementOrder);

    }

    @Override
    public void deleteAbonementOrderById(Long id) {

        var abonementOrder = abonementOrderRepository
                .findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "AbonementOrder class doesn't exist"));

        if (!abonementOrder.isEnabled())
            throw new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "AbonementOrder already disabled");

        log.info("Disabled abonement order: {}", abonementOrder);

        abonementOrder.setEnabled(false);

        abonementOrderRepository.save(abonementOrder);

    }


}
