package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.request.RequestCreateOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailOrderDto;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.mappers.custom.OrderCustomMapper;
import kz.miniland.minilandserver.repositories.OrderRepository;
import kz.miniland.minilandserver.services.OrderService;
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
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderCustomMapper orderCustomMapper;
    private int reqCount = 0;
    @Override
    public void createOrder(RequestCreateOrderDto requestCreateOrderDto) {
        var orderEntity = orderCustomMapper.toEntity(requestCreateOrderDto);
        var entity = orderRepository.save(orderEntity);
        log.info("{}", entity);
    }

    @Override
    public List<ResponseCardOrderDto> getOrderCards() {
        var orderEntities = orderRepository.findAll();
        log.info("{}", reqCount++);
        return orderCustomMapper.toCardDto(orderEntities);
    }

    @Override
    public ResponseDetailOrderDto getDetailOrderById(Long id) {
        var orderEntity = orderRepository.findById(id).orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Order doesn't exist"));
        return orderCustomMapper.toDetailDto(orderEntity);
    }

    @Override
    public void finishOrderById(Long id, Boolean isPaid) {
        var orderEntity = orderRepository.findById(id).orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Order doesn't exist"));
        if(!isPaid) throw new IllegalArgumentException("Order must be paid before finishing!");
        orderEntity.setIsPaid(true);
        orderEntity.setIsFinished(true);
        orderEntity.setFinishedAt(LocalDateTime.now(ZONE_ID));
        orderRepository.save(orderEntity);
    }


}
