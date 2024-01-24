package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.RequestCreateOrderDto;
import kz.miniland.minilandserver.dtos.ResponseCardOrderDto;
import kz.miniland.minilandserver.dtos.ResponseDetailOrderDto;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.mappers.custom.OrderCustomMapper;
import kz.miniland.minilandserver.repositories.OrderRepository;
import kz.miniland.minilandserver.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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
        var orderEntity = orderRepository.findById(id).orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Order doesn't exist"));
        return orderCustomMapper.toDetailDto(orderEntity);
    }
}
