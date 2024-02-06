package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.request.RequestCreateRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.*;

import java.util.List;

public interface RoomService {

    List<ResponseCardRoomOrderDto> getAllCurrentActiveRooms();

    ResponseCardRoomTariffDto getTariffById(Long id);

    List<ResponseCardRoomTariffDto> getAllTariffsByEnabled(Boolean enabled);

    List<ResponseBookedDayDto> getBookedDaysAfterToday();

    ResponseDetailRoomOrderDto getOrderDetailById(Long id);

    void createRoomOrder(RequestCreateRoomOrderDto requestCreateRoomOrderDto);
}
