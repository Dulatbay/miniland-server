package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.RequestCreateOrderDto;
import kz.miniland.minilandserver.dtos.ResponseCardOrderDto;
import kz.miniland.minilandserver.dtos.ResponseDetailOrderDto;

import java.util.List;

public interface OrderService {
    void createOrder(RequestCreateOrderDto requestCreateOrderDto);

    List<ResponseCardOrderDto> getOrderCards();

    ResponseDetailOrderDto getDetailOrderById(Long id);

    void finishOrderById(Long id, Boolean isPaid);

}
