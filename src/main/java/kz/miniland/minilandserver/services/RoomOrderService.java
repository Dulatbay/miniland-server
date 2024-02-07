package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.request.RequestCreateRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.*;

import java.util.List;

public interface RoomOrderService {

    List<ResponseCardRoomOrderDto> getAllCurrentActiveRooms();

    List<ResponseBookedDayDto> getBookedDaysAfterToday();

    ResponseDetailRoomOrderDto getOrderDetailById(Long id);

    void createRoomOrder(RequestCreateRoomOrderDto requestCreateRoomOrderDto);
}
