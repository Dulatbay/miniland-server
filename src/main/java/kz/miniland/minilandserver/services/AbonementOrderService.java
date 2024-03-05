package kz.miniland.minilandserver.services;


import kz.miniland.minilandserver.dtos.request.RequestCreateAbonementOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseAbonementOrderDto;

import java.util.List;

public interface AbonementOrderService {

    List<ResponseAbonementOrderDto> getAllAbonementOrders();

    void createAbonementOrder(RequestCreateAbonementOrderDto requestCreateAbonementOrderDto);

    ResponseAbonementOrderDto getAbonementOrderByPhoneNumber(String phoneNumber);

    void deleteAbonementOrderById(Long id);

}
