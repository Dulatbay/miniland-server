package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.request.RequestCreateAbonementOrderDto;
import kz.miniland.minilandserver.dtos.request.RequestCreateOrderByAbonementDto;
import kz.miniland.minilandserver.dtos.request.RequestCreateOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardMasterClassDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseOrderCountDto;
import kz.miniland.minilandserver.entities.Order;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.mappers.MasterClassMapper;
import kz.miniland.minilandserver.mappers.custom.OrderCustomMapper;
import kz.miniland.minilandserver.repositories.AbonementOrderRepository;
import kz.miniland.minilandserver.repositories.OrderMasterClassRepository;
import kz.miniland.minilandserver.repositories.OrderRepository;
import kz.miniland.minilandserver.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static kz.miniland.minilandserver.constants.ValueConstants.ZONE_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderCustomMapper orderCustomMapper;
    private final OrderMasterClassRepository orderMasterClassRepository;
    private final MasterClassMapper masterClassMapper;
    private final AbonementOrderRepository abonementOrderRepository;

    @Override
    public void createOrder(RequestCreateOrderDto requestCreateOrderDto) {
        var orderEntity = orderCustomMapper.toEntity(requestCreateOrderDto);
        var entity = orderRepository.save(orderEntity);
        log.info("saved order: {}", entity);
    }

    @Override
    public List<ResponseCardOrderDto> getTodaysOrderCards() {
        var now = LocalDate.now(ZONE_ID);

        var startOfDay = LocalDateTime.of(now, LocalTime.MIN);
        var endOfDay = LocalDateTime.of(now, LocalTime.MAX);

        var orderEntities = orderRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        log.info("startOfDay: {}, endOfDay: {}, order count today: {}", startOfDay, endOfDay, orderEntities.size());

        return orderCustomMapper.toCardDto(orderEntities);
    }

    @Override
    public ResponseDetailOrderDto getDetailOrderById(Long id) {
        var orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Order doesn't exist"));
        return orderCustomMapper.toDetailDto(orderEntity);
    }

    @Override
    public void finishOrderById(Long id, Boolean isPaid) {
        var orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Order doesn't exist"));
        if (!isPaid)
            throw new IllegalArgumentException("Order must be paid before finishing!");
        orderEntity.setIsPaid(true);
        orderEntity.setIsFinished(true);
        orderEntity.setFinishedAt(LocalDateTime.now(ZONE_ID));
        orderRepository.save(orderEntity);
    }

    @Override
    public List<ResponseCardMasterClassDto> getMasterClassesByOrderId(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Order doesn't exist"));

        var orderMasterClassList = orderMasterClassRepository.findAllByOrder(order);



        return orderMasterClassList
                .stream().filter(i -> i.getMasterClass().getEnabled())
                .map(i -> masterClassMapper.toDto(i.getMasterClass()))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseOrderCountDto getOrderCountByPhoneNumber(String phoneNumber) {

        if(phoneNumber == null || phoneNumber.trim().isEmpty()){

            log.error("Phone number can't be empty");

            throw new IllegalArgumentException("Phone number can't be empty");

        }

        var orderCount = orderRepository.countOrdersByPhoneNumber(phoneNumber);

        log.info("Total order count with phone number {} is: {}", phoneNumber, orderCount);

        return ResponseOrderCountDto.builder()
                .orderCount(orderCount)
                .build();

    }

    @Override
    public void createOrderByAbonement(RequestCreateOrderByAbonementDto requestCreateOrderByAbonementDto) {

        var abonementOrder = abonementOrderRepository
                .findById(requestCreateOrderByAbonementDto.getAbonementOrderId())
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "BaseAbonement doesn't exist"));

        if (!abonementOrder.getPhoneNumber().equals(requestCreateOrderByAbonementDto.getPhoneNumber())) {

            log.error("Requested phone number and abonement phone number not the same");

            throw new IllegalArgumentException("Requested phone number and abonement phone number not the same");

        }

        if (!abonementOrder.isEnabled()) {

            log.error("AbonementOrder does not enable");

            throw new IllegalArgumentException("AbonementOrder does not enable");

        }

        if (abonementOrder.getQuantity() < 1) {

            log.error("Requested abonement order has a 0 quantity! It is not enable");

            throw new IllegalArgumentException("Requested abonement order has a 0 quantity! It is not enable");


        }

        var order = Order.builder()
                .authorName(requestCreateOrderByAbonementDto.getAuthorId())
                .childAge(abonementOrder.getChildAge())
                .childName(abonementOrder.getChildName())
                .parentName(abonementOrder.getClientName())
                .phoneNumber(abonementOrder.getPhoneNumber())
                .isPaid(true)
                .createdAt(LocalDateTime.now(ZONE_ID))
                .fullPrice(abonementOrder.getBaseAbonement().getFullPrice())
                .fullTime(abonementOrder.getBaseAbonement().getFullTime())
                .extraTime(0L)
                .isFinished(false)
                .build();

        abonementOrder.setQuantity(abonementOrder.getQuantity() - 1);


        log.info("Created new order: {}", order);

        abonementOrderRepository.save(abonementOrder);
        orderRepository.save(order);

    }
}



