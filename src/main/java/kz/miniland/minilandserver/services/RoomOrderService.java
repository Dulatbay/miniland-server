package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.request.RequestCreateRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseBookedDayDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailRoomOrderDto;

import java.util.List;

public interface RoomOrderService {

    List<ResponseCardRoomOrderDto> getAllCurrentActiveRooms();

    List<ResponseBookedDayDto> getBookedDaysAfterToday(Long id);

    ResponseDetailRoomOrderDto getOrderDetailById(Long id);

    void createRoomOrder(RequestCreateRoomOrderDto requestCreateRoomOrderDto);
}
