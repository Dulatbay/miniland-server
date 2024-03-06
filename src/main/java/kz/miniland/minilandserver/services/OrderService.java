package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.request.RequestCreateAbonementOrderDto;
import kz.miniland.minilandserver.dtos.request.RequestCreateOrderByAbonementDto;
import kz.miniland.minilandserver.dtos.request.RequestCreateOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardMasterClassDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseOrderCountDto;

import java.util.List;

public interface OrderService {
    void createOrder(RequestCreateOrderDto requestCreateOrderDto);

    List<ResponseCardOrderDto> getTodaysOrderCards();

    ResponseDetailOrderDto getDetailOrderById(Long id);

    void finishOrderById(Long id, Boolean isPaid);

    List<ResponseCardMasterClassDto> getMasterClassesByOrderId(Long id);

    ResponseOrderCountDto getOrderCountByPhoneNumber(String phoneNumber);

    void createOrderByAbonement(RequestCreateOrderByAbonementDto requestCreateOrderByAbonementDto);

}
