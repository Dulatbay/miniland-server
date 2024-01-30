package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.request.RequestCreateOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailOrderDto;

import java.util.List;

public interface OrderService {
    void createOrder(RequestCreateOrderDto requestCreateOrderDto);

    List<ResponseCardOrderDto> getOrderCards();

    ResponseDetailOrderDto getDetailOrderById(Long id);

    void finishOrderById(Long id, Boolean isPaid);

}
